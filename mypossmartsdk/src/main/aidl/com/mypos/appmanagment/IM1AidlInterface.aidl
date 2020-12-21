// ISamModuleAidlInterface.aidl
package com.mypos.appmanagment;
import com.mypos.appmanagment.AuthEntity;

// Declare any non-default types here with import statements

interface IM1AidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void open();
    void close();
    boolean detect(long timeoutMillis);
    String readUid();
    int authority(in AuthEntity inEntity);
    int readBlock(int blockNo, out byte[] data);
    int writeBlock(int blockNo, in byte[] data);
    int readBlockValue(int blockNo, out int[] value);
    int writeBlockValue(int blockNo, int value);
}
