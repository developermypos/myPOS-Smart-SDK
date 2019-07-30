package com.mypos.smartsdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class WorkerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra(MyPOSUtil.INTENT_PAYMENT)) {
            try {
                MyPOSAPI.openPaymentActivity(this, (MyPOSPayment) getIntent().getSerializableExtra(MyPOSUtil.INTENT_PAYMENT), 101);
            } catch (IllegalArgumentException e) {
                sendBroadcast(new Intent(MyPOSUtil.BLOCKING_TRANSACTION_RESULT));
                finish();
            }
        } else if (getIntent().hasExtra(MyPOSUtil.INTENT_REFUND)) {
            try {
                MyPOSAPI.openRefundActivity(this, (MyPOSRefund) getIntent().getSerializableExtra(MyPOSUtil.INTENT_REFUND), 101);
            } catch (IllegalArgumentException e) {
                sendBroadcast(new Intent(MyPOSUtil.BLOCKING_TRANSACTION_RESULT));
                finish();
            }
        } else if (getIntent().hasExtra(MyPOSUtil.INTENT_VOID)) {
            try {
                MyPOSAPI.openVoidActivity(this, (MyPOSVoid) getIntent().getSerializableExtra(MyPOSUtil.INTENT_VOID), 101, false);
            } catch (IllegalArgumentException e) {
                sendBroadcast(new Intent(MyPOSUtil.BLOCKING_TRANSACTION_RESULT));
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            sendBroadcast(new Intent(MyPOSUtil.BLOCKING_TRANSACTION_RESULT).putExtras(data.getExtras()).putExtra("result_code", resultCode));
            finish();
        }
    }
}
