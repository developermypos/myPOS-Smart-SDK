package com.mypos.smartsdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;

import com.mypos.appmanagment.AuthEntity;
import com.mypos.appmanagment.IM1AidlInterface;
import com.mypos.appmanagment.ISystemAidlInterface;

import java.net.BindException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class M1Management {

    private static final String SERVICE_ACTION = "com.mypos.service.SYSTEM";
    private static final String M1 = "m1";

    private IM1AidlInterface m1ManagementService = null;

    private boolean isBound = false;
    private static M1Management instance;

    private OnBindListener mListener = null;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ISystemAidlInterface systemService = ISystemAidlInterface.Stub.asInterface(iBinder);

            IBinder binder = null;
            try {
                binder = systemService.getManager(M1);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            if (binder != null)
                m1ManagementService = IM1AidlInterface.Stub.asInterface(binder);

            isBound = true;

            if (mListener != null)
                mListener.onBindComplete();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            m1ManagementService = null;
            isBound = false;
        }
    };

    private M1Management(){

    }

    public static M1Management getInstance() {
        if (instance == null)
            instance = new M1Management();

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
            m1ManagementService = null;
            isBound = false;
        }

        mListener = null;
    }

    public void open(long timeOut) throws Exception {
        if (!isBound) {
            throw new BindException("call .bind(context) fist");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < timeOut)) {
            try {
                if (m1ManagementService != null) {
                    m1ManagementService.open();
                    return;
                }
            }
            catch (IllegalStateException ignored) {
            }
            catch (RemoteException e) {
                e.printStackTrace();
                return;
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

    public void close(long timeOut) throws Exception {
        if (!isBound) {
            throw new BindException("call .bind(context) fist");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < timeOut)) {
            try {
                if (m1ManagementService != null) {
                    m1ManagementService.close();
                    return;
                }
            }
            catch (IllegalStateException ignored) {
            }
            catch (RemoteException e) {
                e.printStackTrace();
                return;
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

    public boolean detect(long timeOut) throws Exception {
        if (!isBound) {
            throw new BindException("call .bind(context) fist");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < timeOut)) {
            try {
                if (m1ManagementService != null) {
                    boolean detect = m1ManagementService.detect(timeOut);

                    return detect;
                }
            }
            catch (IllegalStateException ignored) {
            }
            catch (RemoteException e) {
                e.printStackTrace();
                return false;
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

    public String readUid(long timeOut) throws Exception {
        if (!isBound) {
            throw new BindException("call .bind(context) fist");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < timeOut)) {
            try {
                if (m1ManagementService != null) {
                    String uid = m1ManagementService.readUid();

                    return uid;
                }
            }
            catch (IllegalStateException ignored) {
            }
            catch (RemoteException e) {
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

        throw new TimeoutException("Function did not return result in the required time period");
    }

    public  int authority(AuthEntity auth, long timeOut) throws Exception {
        if (!isBound) {
            throw new BindException("call .bind(context) fist");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < timeOut)) {
            try {
                if (m1ManagementService != null) {
                    int authority = m1ManagementService.authority(auth);

                    return authority;
                }
            }
            catch (IllegalStateException ignored) {
            }
            catch (RemoteException e) {
                e.printStackTrace();
                return -2;
            }
            finally {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        throw new TimeoutException("Function did not return result in the required time period");
    }

    public int readBlock(int blockNo, byte[] dataOut, long timeOut) throws Exception {
        if (!isBound) {
            throw new BindException("call .bind(context) fist");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < timeOut)) {
            try {
                if (m1ManagementService != null) {
                    int ret = m1ManagementService.readBlock(blockNo, dataOut);

                    return ret;
                }
            }
            catch (IllegalStateException ignored) {
            }
            catch (RemoteException e) {
                e.printStackTrace();
                return -2;
            }
            finally {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        throw new TimeoutException("Function did not return result in the required time period");
    }

    public  int writeBlock(int blockNo, byte[] data, long timeOut) throws Exception {
        if (!isBound) {
            throw new BindException("call .bind(context) fist");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < timeOut)) {
            try {
                if (m1ManagementService != null) {
                    int writeBlock = m1ManagementService.writeBlock(blockNo, data);

                    return writeBlock;
                }
            }
            catch (IllegalStateException ignored) {
            }
            catch (RemoteException e) {
                e.printStackTrace();
                return -2;
            }
            finally {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        throw new TimeoutException("Function did not return result in the required time period");
    }


    public int readBlockValue(int blockNo, int[] valueReference, long timeOut) throws Exception {
        if (!isBound) {
            throw new BindException("call .bind(context) fist");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < timeOut)) {
            try {
                if (m1ManagementService != null) {
                    int ret = m1ManagementService.readBlockValue(blockNo, valueReference);

                    return ret;
                }
            }
            catch (IllegalStateException ignored) {
            }
            catch (RemoteException e) {
                e.printStackTrace();
                return -2;
            }
            finally {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        throw new TimeoutException("Function did not return result in the required time period");
    }

    public  int writeBlockValue(int blockNo, int value, long timeOut) throws Exception {
        if (!isBound) {
            throw new BindException("call .bind(context) fist");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < timeOut)) {
            try {
                if (m1ManagementService != null) {
                    int writeBlock = m1ManagementService.writeBlockValue(blockNo, value);

                    return writeBlock;
                }
            }
            catch (IllegalStateException ignored) {
            }
            catch (RemoteException e) {
                e.printStackTrace();
                return -2;
            }
            finally {
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
        return m1ManagementService != null;
    }

}
