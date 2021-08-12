package com.mypos.smartsdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;

import com.mypos.appmanagment.ISystemAidlInterface;
import com.mypos.appmanagment.INetworkAidlInterface;

import java.net.BindException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class NetworkManagement {

    private static final String SERVICE_ACTION = "com.mypos.service.SYSTEM";
    private static final String NETWORK = "network";

    private INetworkAidlInterface networkManagementService = null;

    private boolean isBound = false;
    private static NetworkManagement instance;

    private OnBindListener mListener = null;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ISystemAidlInterface systemService = ISystemAidlInterface.Stub.asInterface(iBinder);

            IBinder binder = null;
            try {
                binder = systemService.getManager(NETWORK);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            if (binder != null)
                networkManagementService = INetworkAidlInterface.Stub.asInterface(binder);

            isBound = true;

            if (mListener != null)
                mListener.onBindComplete();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            networkManagementService = null;
            isBound = false;
        }
    };

    private NetworkManagement(){

    }

    public static NetworkManagement getInstance() {
        if (instance == null)
            instance = new NetworkManagement();

        return instance;
    }

    public void bind(Context context, OnBindListener listener) throws Exception {
        if (!isServiceExist(context))
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
            networkManagementService = null;
            isBound = false;
        }

        mListener = null;
    }

    public int switchDataNetwork(int whitch, long timeOut) throws Exception {
        if (!isBound) {
            throw new BindException("call .bind(context) fist");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < timeOut)) {
            try {
                if (networkManagementService != null) {
                    int result = networkManagementService.switchDataNetwork(whitch);

                    return result;
                }
            }
            catch (IllegalStateException ignored) {
            }
            catch (RemoteException e) {
                e.printStackTrace();
                return -2;
            } finally {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        throw new TimeoutException("Function did not return result in the required time period");
    }

    private boolean isServiceExist(Context context) {
        Intent intent = new Intent(SERVICE_ACTION, null);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

        List<ResolveInfo> services = context.getPackageManager().queryIntentServices(intent, 0);
        return !services.isEmpty();
    }

    public boolean isSupported() {
        return networkManagementService != null;
    }

}
