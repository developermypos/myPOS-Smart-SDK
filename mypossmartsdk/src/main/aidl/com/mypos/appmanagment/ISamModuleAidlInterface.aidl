// ISamModuleAidlInterface.aidl
package com.mypos.appmanagment;

// Declare any non-default types here with import statements

interface ISamModuleAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    boolean detect(int slot);
    byte[] open(int slot);
    void close(int slot);
    byte[] isoCommand(int slot, in byte[] apduSend);
    String getError();
}
