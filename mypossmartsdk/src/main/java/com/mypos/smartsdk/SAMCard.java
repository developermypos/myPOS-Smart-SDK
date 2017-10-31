package com.mypos.smartsdk;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

/**
 * Created by rumen.ivanov on 24.10.2017 г..
 */

public class SAMCard {
    public static void detect(Activity activity, int requestCode, int slot) {
        Intent intent = new Intent(MyPOSUtil.INTENT_SAM_CARD);

        intent.putExtra(MyPOSUtil.INTENT_SAM_CARD_COMMAND, MyPOSUtil.INTENT_SAM_CARD_COMMAND_DETECT);
        intent.putExtra(MyPOSUtil.INTENT_SAM_CARD_SLOT, slot);

        activity.startActivityForResult(intent, requestCode);
    }

    public static void open(Activity activity, int requestCode, int slot) {
        Intent intent = new Intent(MyPOSUtil.INTENT_SAM_CARD);

        intent.putExtra(MyPOSUtil.INTENT_SAM_CARD_COMMAND, MyPOSUtil.INTENT_SAM_CARD_COMMAND_OPEN);
        intent.putExtra(MyPOSUtil.INTENT_SAM_CARD_SLOT, slot);

        activity.startActivityForResult(intent, requestCode);
    }

    public static void close(Activity activity, int requestCode, int slot) {
        Intent intent = new Intent(MyPOSUtil.INTENT_SAM_CARD);

        intent.putExtra(MyPOSUtil.INTENT_SAM_CARD_COMMAND, MyPOSUtil.INTENT_SAM_CARD_COMMAND_CLOSE);
        intent.putExtra(MyPOSUtil.INTENT_SAM_CARD_SLOT, slot);

        activity.startActivityForResult(intent, requestCode);
    }

    public static void isoCommand(Activity activity, int requestCode, int slot, byte[] apduSend) {
        Intent intent = new Intent(MyPOSUtil.INTENT_SAM_CARD);

        intent.putExtra(MyPOSUtil.INTENT_SAM_CARD_COMMAND, MyPOSUtil.INTENT_SAM_CARD_COMMAND_ISOCOMMAND);
        intent.putExtra(MyPOSUtil.INTENT_SAM_CARD_SLOT, slot);
        intent.putExtra(MyPOSUtil.INTENT_SAM_CARD_REQUEST, apduSend);

        activity.startActivityForResult(intent, requestCode);
    }
}
