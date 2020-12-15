// ISamModuleAidlInterface.aidl
package com.mypos.appmanagment;

// Declare any non-default types here with import statements

interface IUltralightAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void open();
    void close();
    boolean detect(long timeoutMillis);
    int authority(in byte[] var1);
    byte[] readBlock(byte var1);
    int writeBlock(in byte var1,in byte[] var2);
    byte[] exchangeCmd(in byte[] var1);
}
