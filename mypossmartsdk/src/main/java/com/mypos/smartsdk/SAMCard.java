package com.mypos.smartsdk;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.ConditionVariable;
import android.os.Looper;

import java.util.concurrent.TimeoutException;

/**
 * Created by rumen.ivanov on 24.10.2017 г..
 */

public class SAMCard {
    public static boolean detect(Context context, int slot, long timeOut) throws Exception {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Must not be invoked from the main thread.");
        }

        final int[] mResult = {0};
        final boolean[] mResultValue = {false};
        final ConditionVariable mCondition = new ConditionVariable(false);
        final String[] error_msg = {null};

        if (timeOut <= 100) timeOut = 100;

        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent resultIntent) {
                mResult[0] = resultIntent.getIntExtra("status", 0);
                if (mResult[0] == Activity.RESULT_OK) {
                    mResultValue[0] = resultIntent.getBooleanExtra(MyPOSUtil.INTENT_SAM_CARD_RESPONSE, false);
                }
                else {
                    error_msg[0] = resultIntent.getStringExtra("error_msg");
                }
                mCondition.open();
            }
        };
        context.registerReceiver(broadcastReceiver, new IntentFilter(MyPOSUtil.SAM_CARD_RESPONSE_BROADCAST));

        Intent intent = new Intent(MyPOSUtil.INTENT_SAM_CARD);
        intent.putExtra(MyPOSUtil.INTENT_SAM_CARD_COMMAND, MyPOSUtil.INTENT_SAM_CARD_COMMAND_DETECT);
        intent.putExtra(MyPOSUtil.INTENT_SAM_CARD_SLOT, slot);
        MyPOSAPI.sendExplicitBroadcast(context, intent);

        boolean returned = mCondition.block(timeOut); // return false if timeout
        context.unregisterReceiver(broadcastReceiver);

        if (!returned) {
            throw new TimeoutException("Function did not return result in the required time period");
        }

        if (mResult[0] == Activity.RESULT_OK) {
            return mResultValue[0];
        }
        else {
            throw new Exception(error_msg[0]);
        }
    }

    public static byte[] open(Context context, int slot, long timeOut) throws Exception {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Must not be invoked from the main thread.");
        }

        final int[] mResult = {0};
        final byte[][] mResultValue = {null};
        final ConditionVariable mCondition = new ConditionVariable(false);
        final String[] error_msg = {null};

        if (timeOut <= 100) timeOut = 100;

        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent resultIntent) {
                mResult[0] = resultIntent.getIntExtra("status", 0);
                if (mResult[0] == Activity.RESULT_OK) {
                    mResultValue[0] = resultIntent.getByteArrayExtra(MyPOSUtil.INTENT_SAM_CARD_RESPONSE);
                }
                else {
                    error_msg[0] = resultIntent.getStringExtra("error_msg");
                }
                mCondition.open();
            }
        };
        context.registerReceiver(broadcastReceiver, new IntentFilter(MyPOSUtil.SAM_CARD_RESPONSE_BROADCAST));

        Intent intent = new Intent(MyPOSUtil.INTENT_SAM_CARD);
        intent.putExtra(MyPOSUtil.INTENT_SAM_CARD_COMMAND, MyPOSUtil.INTENT_SAM_CARD_COMMAND_OPEN);
        intent.putExtra(MyPOSUtil.INTENT_SAM_CARD_SLOT, slot);
        MyPOSAPI.sendExplicitBroadcast(context, intent);

        boolean returned = mCondition.block(timeOut); // return false if timeout
        context.unregisterReceiver(broadcastReceiver);

        if (!returned) {
            throw new TimeoutException("Function did not return result in the required time period");
        }

        if (mResult[0] == Activity.RESULT_OK) {
            return mResultValue[0];
        }
        else {
            throw new Exception(error_msg[0]);
        }
    }

    public static void close(Context context, int slot, long timeOut) throws Exception {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Must not be invoked from the main thread.");
        }

        final int[] mResult = {0};
        final ConditionVariable mCondition = new ConditionVariable(false);
        final String[] error_msg = {null};

        if (timeOut <= 100) timeOut = 100;

        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent resultIntent) {
                mResult[0] = resultIntent.getIntExtra("status", 0);
                if (mResult[0] == Activity.RESULT_OK) {
                }
                else {
                    error_msg[0] = resultIntent.getStringExtra("error_msg");
                }
                mCondition.open();
            }
        };
        context.registerReceiver(broadcastReceiver, new IntentFilter(MyPOSUtil.SAM_CARD_RESPONSE_BROADCAST));

        Intent intent = new Intent(MyPOSUtil.INTENT_SAM_CARD);
        intent.putExtra(MyPOSUtil.INTENT_SAM_CARD_COMMAND, MyPOSUtil.INTENT_SAM_CARD_COMMAND_CLOSE);
        intent.putExtra(MyPOSUtil.INTENT_SAM_CARD_SLOT, slot);
        MyPOSAPI.sendExplicitBroadcast(context, intent);

        boolean returned = mCondition.block(timeOut); // return false if timeout
        context.unregisterReceiver(broadcastReceiver);

        if (!returned) {
            throw new TimeoutException("Function did not return result in the required time period");
        }

        if (mResult[0] == Activity.RESULT_OK) {
            return;
        }
        else {
            throw new Exception(error_msg[0]);
        }
    }

    public static byte[] isoCommand(Context context, int slot, long timeOut, byte[] apduSend) throws Exception {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Must not be invoked from the main thread.");
        }

        final int[] mResult = {0};
        final byte[][] mResultValue = {null};
        final ConditionVariable mCondition = new ConditionVariable(false);
        final String[] error_msg = {null};

        if (timeOut <= 100) timeOut = 100;

        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent resultIntent) {
                mResult[0] = resultIntent.getIntExtra("status", 0);
                if (mResult[0] == Activity.RESULT_OK) {
                    mResultValue[0] = resultIntent.getByteArrayExtra(MyPOSUtil.INTENT_SAM_CARD_RESPONSE);
                }
                else {
                    error_msg[0] = resultIntent.getStringExtra("error_msg");
                }
                mCondition.open();
            }
        };
        context.registerReceiver(broadcastReceiver, new IntentFilter(MyPOSUtil.SAM_CARD_RESPONSE_BROADCAST));

        Intent intent = new Intent(MyPOSUtil.INTENT_SAM_CARD);
        intent.putExtra(MyPOSUtil.INTENT_SAM_CARD_COMMAND, MyPOSUtil.INTENT_SAM_CARD_COMMAND_ISOCOMMAND);
        intent.putExtra(MyPOSUtil.INTENT_SAM_CARD_SLOT, slot);
        intent.putExtra(MyPOSUtil.INTENT_SAM_CARD_REQUEST, apduSend);
        MyPOSAPI.sendExplicitBroadcast(context, intent);

        boolean returned = mCondition.block(timeOut); // return false if timeout
        context.unregisterReceiver(broadcastReceiver);

        if (!returned) {
            throw new TimeoutException("Function did not return result in the required time period");
        }

        if (mResult[0] == Activity.RESULT_OK) {
            return mResultValue[0];
        }
        else {
            throw new Exception(error_msg[0]);
        }
    }
}
