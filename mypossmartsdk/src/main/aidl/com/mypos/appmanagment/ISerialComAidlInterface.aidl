// ISerialComAidlInterface.aidl
package com.mypos.appmanagment;

// Declare any non-default types here with import statements

interface ISerialComAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int connect();
    int disconnect();
    int send(String data);
    String recv(int len, long timeout);
}
