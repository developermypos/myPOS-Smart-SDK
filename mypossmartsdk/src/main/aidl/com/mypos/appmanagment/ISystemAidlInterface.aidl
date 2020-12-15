// ISystemAidlInterface.aidl
package com.mypos.appmanagment;

// Declare any non-default types here with import statements

interface ISystemAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    IBinder getManager(String key);
}
