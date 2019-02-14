package com.mypos.smartsdk;

import android.content.Context;

import com.mypos.appmanagment.SerialComManagement;

import java.net.BindException;

public class SerialCOM {

    private SerialComManagement serialComManagement;
    private static SerialCOM instance;

    public static SerialCOM getInstance() {

        if (instance == null)
            instance = new SerialCOM();

        return instance;
    }

    public SerialCOM() {
        serialComManagement = SerialComManagement.getInstance();
    }

    public void bind(Context context) throws Exception {
        serialComManagement.bind(context);
    }
    public void unbind(Context context) {
        serialComManagement.unbind(context);
    }
    public int connect(long timeout) throws BindException {
        return serialComManagement.connect(timeout);
    }
    public int disconnect(long timeout) throws BindException {
        return serialComManagement.disconnect(timeout);
    }
    public int send(String data, long timeout) throws BindException {
        return serialComManagement.send(data, timeout);
    }
    public String recv(int len, long timeout) throws BindException {
        return serialComManagement.recv(len, timeout);
    }
}
