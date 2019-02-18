package com.mypos.smartsdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;

import com.mypos.appmanagment.ISerialComAidlInterface;
import com.mypos.appmanagment.ISystemAidlInterface;

import java.net.BindException;
import java.util.List;

public class SerialComManagement {

    private static final String SERVICE_ACTION = "com.mypos.service.SYSTEM";
    private static final String SERIAL_COM = "serial_com";

    private ISystemAidlInterface systemService = null;
    private ISerialComAidlInterface serialComService = null;

    private boolean isBound = false;
    private static SerialComManagement instance;

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
                serialComService = ISerialComAidlInterface.Stub.asInterface(binder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            systemService = null;
            serialComService = null;
            isBound = false;
        }
    };

    private SerialComManagement(){

    }

    public static SerialComManagement getInstance() {
        if (instance == null)
            instance = new SerialComManagement();

        return instance;
    }

    public void bind(Context context) throws Exception {
        if (!isSupported(context))
            throw new Exception("Functionality not supported (probably old version of myPOS OS)");

        Intent intent = new Intent(SERVICE_ACTION);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setPackage("com.mypos");

        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        isBound = true;
    }

    public void unbind(Context context) {
        if (isBound)
            context.unbindService(serviceConnection);

        isBound = false;
    }

    public int connect(long timeout) throws BindException {
        if (!isBound) {
            throw new BindException("call .bind(context) fist");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < timeout)) {
            try {
                if (serialComService != null)
                    return serialComService.connect();
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

    public int disconnect(long timeout) throws BindException {
        if (!isBound) {
            throw new BindException("call .bind(context) fist");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < timeout)) {
            try {
                if (serialComService != null)
                    return serialComService.disconnect();
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

    public int send(byte[] data, long timeout) throws BindException {
        if (!isBound) {
            throw new BindException("call .bind(context) fist");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < timeout)) {
            try {
                if (serialComService != null)
                    return serialComService.send(data);
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

    public byte[] recv(int len, long timeout) throws BindException {
        if (!isBound) {
            throw new BindException("call .bind(context) fist");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < timeout)) {
            try {
                if (serialComService != null)
                    return serialComService.recv(len, timeout);
            }
            catch (IllegalStateException ignored) {
            } catch (RemoteException e) {
                e.printStackTrace();
                return null;
            }
            finally {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public boolean isSupported(Context context) {
        Intent intent = new Intent(SERVICE_ACTION, null);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

        List<ResolveInfo> services = context.getPackageManager().queryIntentServices(intent, 0);
        return !services.isEmpty();
    }

}
