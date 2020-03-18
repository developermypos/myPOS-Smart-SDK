package com.mypos.smartsdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;

import com.google.gson.Gson;
import com.mypos.appmanagment.IPrinterAidlInterface;
import com.mypos.appmanagment.ISystemAidlInterface;
import com.mypos.smartsdk.print.PrinterCommand;

import java.net.BindException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class PrinterManagement {

    private static final String SERVICE_ACTION = "com.mypos.service.SYSTEM";
    private static final String SAM_MODULE = "printer";

    private IPrinterAidlInterface printerManagementService = null;

    private boolean isBound = false;
    private static PrinterManagement instance;

    private OnBindListener mListener = null;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ISystemAidlInterface systemService = ISystemAidlInterface.Stub.asInterface(iBinder);

            IBinder binder = null;
            try {
                binder = systemService.getManager(SAM_MODULE);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            if (binder != null)
                printerManagementService = IPrinterAidlInterface.Stub.asInterface(binder);

            isBound = true;

            if (mListener != null)
                mListener.onBindComplete();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            printerManagementService = null;
            isBound = false;
        }
    };

    private PrinterManagement(){

    }

    public static PrinterManagement getInstance() {
        if (instance == null)
            instance = new PrinterManagement();

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
            printerManagementService = null;
            isBound = false;
        }

        mListener = null;
    }

    public int print(List<PrinterCommand> commands, boolean printBottomSpace, long timeOut) throws Exception {
        if (!isBound) {
            throw new BindException("call .bind(context) fist");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < timeOut)) {
            try {
                if (printerManagementService != null) {
                    Gson gson = new Gson();
                    String json = gson.toJson(commands);

                    return printerManagementService.print(json, printBottomSpace);
                }
            }
            catch (IllegalStateException ignored) {
            }
            catch (RemoteException e) {
                e.printStackTrace();
                return -1;
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


    public int getPrinterStatus(long timeOut) throws Exception {
        if (!isBound) {
            throw new BindException("call .bind(context) fist");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < timeOut)) {
            try {
                if (printerManagementService != null) {
                    int ret = printerManagementService.getStatus();
                    return ret;
                }
            }
            catch (IllegalStateException ignored) {
            }
            catch (RemoteException e) {
                e.printStackTrace();
                return -1;
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
        return printerManagementService != null;
    }

}
