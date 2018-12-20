package com.mypos.smartsdk;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Environment;
import android.os.Looper;
import android.os.Parcel;
import android.util.Log;

import com.google.gson.Gson;
import com.mypos.smartsdk.print.PrinterCommand;
import com.mypos.smartsdk.print.PrinterStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class Printer {


    public static int print(Context context, List<PrinterCommand> commands, UUID guid, long timeOut) throws IllegalStateException {
        return print(context, commands, guid, timeOut, true, false);
    }

    public static int print(Context context, List<PrinterCommand> commands, UUID guid, long timeOut, boolean printBottomSpace, boolean useFile) throws IllegalStateException {
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

        Bundle extras = new Bundle();
        extras.putString(MyPOSUtil.INTENT_GUID, sentGUID);
        extras.putBoolean(MyPOSUtil.INTENT_RECEIPT_BOTTOM_SPACE, printBottomSpace);

        if (useFile) {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), guid + ".txt");
            try {
                file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(file, true);
                outputStream.write(json.getBytes());
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                return PrinterStatus.PRINTER_STATUS_UNKNOWN_ERROR;
            }

            extras.putString(MyPOSUtil.INTENT_PRINT_DATA_FILE_PATH, file.getAbsolutePath());
        }
        else {
            extras.putString(MyPOSUtil.INTENT_PRINT_COMMANDS, json);
        }

        if (getBundleSizeInBytes(extras) >= 1e+6)
            return PrinterStatus.PRINTER_STATUS_DATA_PACKET_TOO_LONG;

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
        intent.putExtras(extras);
        context.sendBroadcast(intent);

        boolean returned = mCondition.block(timeOut); // return false if timeout
        context.unregisterReceiver(broadcastReceiver);

        if (!returned) {
            mResult[0] = PrinterStatus.PRINTER_NOT_FINISHED;
        }

        return  mResult[0];
    }

    private static int getBundleSizeInBytes(Bundle bundle) {
        Parcel parcel = Parcel.obtain();
        int size;

        parcel.writeBundle(bundle);
        size = parcel.dataSize();
        parcel.recycle();

        return size;
    }

    public static int getPrinterStatus(Context context, long timeOut) throws IllegalStateException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Must not be invoked from the main thread.");
        }

        final ConditionVariable mCondition = new ConditionVariable(false);
        final int mResult[] = {PrinterStatus.PRINTER_STATUS_UNKNOWN_ERROR};

        if (timeOut <= 100) timeOut = 100;

        Log.i("Printer", "Sending printer status broadcast");

        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent resultIntent) {
                mResult[0] = resultIntent.getIntExtra(MyPOSUtil.INTENT_PRINT_STATUS, PrinterStatus.PRINTER_STATUS_UNKNOWN_ERROR);

                mCondition.open();
            }
        };
        context.registerReceiver(broadcastReceiver, new IntentFilter(MyPOSUtil.PRINTER_STATUS_RESPONSE_BROADCAST));

        Intent intent = new Intent(MyPOSUtil.PRINTER_STATUS_BROADCAST);
        context.sendBroadcast(intent);

        boolean returned = mCondition.block(timeOut); // return false if timeout

        context.unregisterReceiver(broadcastReceiver);

        return  mResult[0];
    }
}
