package com.mypos.smartsdk;

import com.mypos.smartsdk.data.POSInfo;


public interface OnPOSInfoListener {
    void onReceive(POSInfo info);
}
