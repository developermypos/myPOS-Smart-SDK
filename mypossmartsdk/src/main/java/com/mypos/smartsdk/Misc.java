package com.mypos.smartsdk;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.ConditionVariable;
import android.os.Looper;
import android.util.Log;

public class Misc {

    public static int ping(Context context, long timeOut) throws IllegalStateException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Must not be invoked from the main thread.");
        }

        final ConditionVariable mCondition = new ConditionVariable(false);
        final int mResult[] = {Activity.RESULT_CANCELED};

        if (timeOut <= 100) timeOut = 100;

        Log.i("Ping", "Sending ping broadcast");

        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent resultIntent) {
                mResult[0] = resultIntent.getIntExtra(MyPOSUtil.INTENT_STATUS, Activity.RESULT_CANCELED);

                mCondition.open();
            }
        };
        context.registerReceiver(broadcastReceiver, new IntentFilter(MyPOSUtil.PING_DONE_BROADCAST));

        Intent intent = new Intent(MyPOSUtil.SEND_PING_BROADCAST);
        context.sendBroadcast(intent);

        boolean returned = mCondition.block(timeOut); // return false if timeout

        context.unregisterReceiver(broadcastReceiver);

        return  mResult[0];
    }

}
