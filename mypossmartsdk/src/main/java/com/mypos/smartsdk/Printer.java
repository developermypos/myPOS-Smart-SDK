package com.mypos.smartsdk;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.ConditionVariable;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.mypos.smartsdk.print.PrinterCommand;
import com.mypos.smartsdk.print.PrinterStatus;

import java.util.List;
import java.util.UUID;

public class Printer {


    public static int print(Context context, List<PrinterCommand> commands, UUID guid, long timeOut) throws IllegalStateException {
        return print(context, commands, guid, timeOut, true);
    }

    public static int print(Context context, List<PrinterCommand> commands, UUID guid, long timeOut, boolean printBottomSpace) throws IllegalStateException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Must not be invoked from the main thread.");
        }

        final ConditionVariable mCondition = new ConditionVariable(false);
        final String sentGUID = guid.toString();
        final int mResult[] = {PrinterStatus.PRINTER_STATUS_UNKNOWN_ERROR};
        String json;

        if (timeOut <= 100) timeOut = 100;

        Gson gson = new Gson();

        json = gson.toJson(commands);

        Log.i("Printer", "Sending print broadcast: " + json);

        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent resultIntent) {
                String receivedGUID = resultIntent.getStringExtra(MyPOSUtil.INTENT_GUID);
                if (receivedGUID != null && !sentGUID.equals(receivedGUID)) {
                    return;
                }

                mResult[0] = resultIntent.getIntExtra(MyPOSUtil.INTENT_PRINT_STATUS, PrinterStatus.PRINTER_STATUS_UNKNOWN_ERROR);

                mCondition.open();
            }
        };
        context.registerReceiver(broadcastReceiver, new IntentFilter(MyPOSUtil.PRINTING_DONE_BROADCAST));

        Intent intent = new Intent(MyPOSUtil.PRINT_BROADCAST);
        intent.putExtra(MyPOSUtil.INTENT_PRINT_COMMANDS, json);
        intent.putExtra(MyPOSUtil.INTENT_GUID, sentGUID);
        intent.putExtra(MyPOSUtil.INTENT_RECEIPT_BOTTOM_SPACE, printBottomSpace);
        context.sendBroadcast(intent);

        boolean returned = mCondition.block(timeOut); // return false if timeout
        context.unregisterReceiver(broadcastReceiver);

        if (!returned) {
            mResult[0] = PrinterStatus.PRINTER_NOT_FINISHED;
        }

        return  mResult[0];
    }

}
