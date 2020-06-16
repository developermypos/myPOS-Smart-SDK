package com.mypos.smartsdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;

import com.mypos.appmanagment.ISystemAidlInterface;
import com.mypos.appmanagment.ITaxControlAidlInterface;

import java.net.BindException;
import java.util.List;

public class TaxControlManagement {

    private static final String SERVICE_ACTION = "com.mypos.service.SYSTEM";
    private static final String SERIAL_COM = "tax_control";

    private ISystemAidlInterface systemService = null;
    private ITaxControlAidlInterface taxControlService = null;

    private boolean isBound = false;
    private static TaxControlManagement instance;

    private OnBindListener mListener = null;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            systemService = ISystemAidlInterface.Stub.asInterface(iBinder);

            IBinder binder = null;
            try {
                binder = systemService.getManager(SERIAL_COM);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            if (binder != null)
                taxControlService = ITaxControlAidlInterface.Stub.asInterface(binder);

            isBound = true;

            if (mListener != null)
                mListener.onBindComplete();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            systemService = null;
            taxControlService = null;
            isBound = false;
        }
    };

    private TaxControlManagement(){

    }

    public static TaxControlManagement getInstance() {
        if (instance == null)
            instance = new TaxControlManagement();

        return instance;
    }

    public void bind(Context context, OnBindListener listener) throws Exception {
        if (!isSupported(context))
            throw new Exception("Functionality not supported (probably old version of myPOS OS)");

        if (isBound)
            return;

        mListener = listener;

        Intent intent = new Intent(SERVICE_ACTION);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setPackage("com.mypos");
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unbind(Context context) {
        if (isBound) {
            context.unbindService(serviceConnection);
            systemService = null;
            taxControlService = null;
            isBound = false;
        }

        mListener = null;
    }

    public int open(long timeout) throws BindException {
        if (!isBound) {
            throw new BindException("call .bind(context) fist");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < timeout)) {
            try {
                if (taxControlService != null)
                    return taxControlService.open();
            }
            catch (IllegalStateException ignored) {
            } catch (RemoteException e) {
                e.printStackTrace();
                return - 2;
            } finally {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return -5;
    }

    public int close(long timeout) throws BindException {
        if (!isBound) {
            throw new BindException("call .bind(context) fist");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < timeout)) {
            try {
                if (taxControlService != null)
                    return taxControlService.close();
            }
            catch (IllegalStateException ignored) {
            } catch (RemoteException e) {
                e.printStackTrace();
                return - 2;
            }
            finally {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return -5;
    }

    public boolean isSupported(Context context) {
        Intent intent = new Intent(SERVICE_ACTION, null);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

        List<ResolveInfo> services = context.getPackageManager().queryIntentServices(intent, 0);
        return !services.isEmpty();
    }
}
