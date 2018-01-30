package com.mypos.smartsdk;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.mypos.smartsdk.data.POSInfo;


public class MyPOSAPI {

    /**
     * Takes care of send and receive broadcast receivers to/from the system
     * AVAILABLE IN VERSION: 1.0.2
     *
     * @param context       this context will be used to for broadcast communication with the system
     * @param listener     a callback listener for received POS info
     */
    public static void registerPOSInfo(final Context context, final OnPOSInfoListener listener) {

        if (context == null)
            return;

        context.sendBroadcast(new Intent(MyPOSUtil.GET_SIMPLE_POS_INFO));

        context.registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context cx, Intent intent) {

                        if (listener != null) {
                            POSInfo inf = new POSInfo();
                            inf.parseFromBundle(intent.getExtras());
                            listener.onReceive(inf);
                        }

                        context.unregisterReceiver(this);
                    }
                },new IntentFilter(MyPOSUtil.GET_SIMPLE_POS_INFO_RESPONSE));
    }

    /**
     * Takes care of building the intent and opening the payment activity
     *
     * @param activity    the activity whose context will be used to start the payment activity
     * @param payment     a {@link MyPOSPayment} object with payment-related data
     * @param requestCode the request code used later to distinguish
     */
    public static void openPaymentActivity(Activity activity, MyPOSPayment payment, int requestCode) {
        openPaymentActivity(activity, payment, requestCode, false, ReceiptPrintMode.AUTOMATICALLY);
    }

    /**
     * Takes care of building the intent and opening the payment activity
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param payment                a {@link MyPOSPayment} object with payment-related data
     * @param requestCode            the request code used later to distinguish
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     */
    public static void openPaymentActivity(Activity activity, MyPOSPayment payment, int requestCode, boolean skipConfirmationScreen) {
        openPaymentActivity(activity, payment, requestCode, skipConfirmationScreen, ReceiptPrintMode.AUTOMATICALLY);
    }

    /**
     * Takes care of building the intent and opening the payment activity
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param payment                a {@link MyPOSPayment} object with payment-related data
     * @param requestCode            the request code used later to distinguish
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     * @param printMode              tells the payment app how receipts should be printed, if any
     */
    public static void openPaymentActivity(Activity activity, MyPOSPayment payment, int requestCode, boolean skipConfirmationScreen, ReceiptPrintMode printMode) {
        Intent myposIntent;
        if (payment.isMotoTransaction()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_MOTO_INTENT);
        } else {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_INTENT);
        }
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_PAYMENT);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, payment.getProductAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_TIP_AMOUNT, payment.getTipAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_RECEIPT_PRINT_MODE, printMode.getValue());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSFER_TIPS_ENABLED, payment.isTippingModeEnabled());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, payment.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, payment.getForeignTransactionId());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, payment.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, payment.getPrintCustomerReceipt());

        activity.startActivityForResult(myposIntent, requestCode);
    }

    /**
     * Takes care of building the intent and opening the payment activity for a refund transaction
     *
     * @param activity    the activity whose context will be used to start the payment activity
     * @param refund      a {@link MyPOSPayment} object with payment-related data
     * @param requestCode the request code used later to distinguish the type of transaction that has completed
     */
    public static void openRefundActivity(Activity activity, MyPOSRefund refund, int requestCode) {
        openRefundActivity(activity, refund, requestCode, false, ReceiptPrintMode.AUTOMATICALLY);
    }

    /**
     * Takes care of building the intent and opening the payment activity for a refund transaction
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param refund                 a {@link MyPOSPayment} object with payment-related data
     * @param requestCode            the request code used later to distinguish the type of transaction that has completed
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     */
    public static void openRefundActivity(Activity activity, MyPOSRefund refund, int requestCode, boolean skipConfirmationScreen) {
        openRefundActivity(activity, refund, requestCode, skipConfirmationScreen, ReceiptPrintMode.AUTOMATICALLY);
    }

    /**
     * Takes care of building the intent and opening the payment activity for a refund transaction
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param refund                 a {@link MyPOSPayment} object with payment-related data
     * @param requestCode            the request code used later to distinguish the type of transaction that has completed
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     * @param printMode              tells the payment app how receipts should be printed, if any
     */
    public static void openRefundActivity(Activity activity, MyPOSRefund refund, int requestCode, boolean skipConfirmationScreen, ReceiptPrintMode printMode) {
        Intent myposIntent;
        if (refund.isMotoTransaction()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_MOTO_INTENT);
        } else {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_INTENT);
        }
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_REFUND);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, refund.getRefundAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_RECEIPT_PRINT_MODE, printMode.getValue());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, refund.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, refund.getForeignTransactionId());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, refund.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, refund.getPrintCustomerReceipt());

        activity.startActivityForResult(myposIntent, requestCode);
    }
    /**
     * Takes care of building the intent and opening the payment activity for a void transaction
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param voidEx                 a {@link MyPOSPayment} object with payment-related data, pass null to void the last transaction
     * @param requestCode            the request code used later to distinguish the type of transaction that has completed
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     */
    public static void openVoidActivity(Activity activity, MyPOSVoidEx voidEx, int requestCode, boolean skipConfirmationScreen) {
        Intent myposIntent;
        if(voidEx == null) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_VOID_INTENT);
        } else {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_VOID_INTENT_EX);

            myposIntent.putExtra(MyPOSUtil.INTENT_VOID_STAN, voidEx.getSTAN());
            myposIntent.putExtra(MyPOSUtil.INTENT_VOID_AUTH_CODE, voidEx.getAuthCode());
            myposIntent.putExtra(MyPOSUtil.INTENT_VOID_DATE_TIME, voidEx.getDateTime());
        }
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_VOID);
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);

        activity.startActivityForResult(myposIntent,requestCode);
    }


    /**
     * Creates a preauthorization request
     *
     * @param activity    the activity whose context will be used to start the payment activity
     * @param preauth     a {@link MyPOSPreauthorization} with transaction-related data
     * @param requestCode the request code used later to distinguish the type of transaction that has completed
     */
    public static void createPreauthorization(Activity activity, MyPOSPreauthorization preauth, int requestCode) {
        createPreauthorization(activity, preauth, requestCode, false, ReceiptPrintMode.AUTOMATICALLY);
    }

    /**
     * Creates a preauthorization request
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param preauth                a {@link MyPOSPreauthorization} with transaction-related data
     * @param requestCode            the request code used later to distinguish the type of transaction that has completed
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     */
    public static void createPreauthorization(Activity activity, MyPOSPreauthorization preauth, int requestCode, boolean skipConfirmationScreen) {
        createPreauthorization(activity, preauth, requestCode, skipConfirmationScreen, ReceiptPrintMode.AUTOMATICALLY);
    }

    /**
     * Create a preauthorization request
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param preauth                a {@link MyPOSPreauthorization} with transaction-related data
     * @param requestCode            the request code used later to distinguish the type of transaction that has completed
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     * @param printMode              tells the payment app how receipts should be printed, if any
     */
    public static void createPreauthorization(Activity activity, MyPOSPreauthorization preauth, int requestCode, boolean skipConfirmationScreen, ReceiptPrintMode printMode) {
        Intent myposIntent;
        if (preauth.isMotoTransaction()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_MOTO_INTENT);
        } else {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_INTENT);
        }

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_PREAUTH);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, preauth.getProductAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_RECEIPT_PRINT_MODE, printMode.getValue());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, preauth.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, preauth.getForeignTransactionId());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, preauth.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, preauth.getPrintCustomerReceipt());

        activity.startActivityForResult(myposIntent, requestCode);
    }

    /**
     * Complete a preauthorization
     *
     * @param activity    the activity whose context will be used to start the payment activity
     * @param preauth     a {@link MyPOSPreauthorizationCompletion} object with transaction-related data
     * @param requestCode the request code used later to distinguish the type of transaction that has completed
     */
    public static void completePreauthorization(Activity activity, MyPOSPreauthorizationCompletion preauth, int requestCode) {
        completePreauthorization(activity, preauth, requestCode, false, ReceiptPrintMode.AUTOMATICALLY);
    }

    /**
     * Complete a preauthorization
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param preauth                a {@link MyPOSPreauthorizationCompletion} object with transaction-related data
     * @param requestCode            the request code used later to distinguish the type of transaction that has completed
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     */
    public static void completePreauthorization(Activity activity, MyPOSPreauthorizationCompletion preauth, int requestCode, boolean skipConfirmationScreen) {
        completePreauthorization(activity, preauth, requestCode, skipConfirmationScreen, ReceiptPrintMode.AUTOMATICALLY);
    }

    /**
     * Complete a preauthorization
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param preauth                a {@link MyPOSPreauthorizationCompletion} object with transaction-related data
     * @param requestCode            the request code used later to distinguish the type of transaction that has completed
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     * @param printMode              tells the payment app how receipts should be printed, if any
     */
    public static void completePreauthorization(Activity activity, MyPOSPreauthorizationCompletion preauth, int requestCode, boolean skipConfirmationScreen, ReceiptPrintMode printMode) {
        Intent myposIntent;
        if (preauth.isMotoTransaction()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_MOTO_INTENT);
        } else {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_INTENT);
        }

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_PREAUTH_COMPLETION);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSFER_PREAUTH_CODE, preauth.getPreauthorizationCode());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, preauth.getProductAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_RECEIPT_PRINT_MODE, printMode.getValue());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, preauth.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, preauth.getForeignTransactionId());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, preauth.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, preauth.getPrintCustomerReceipt());

        activity.startActivityForResult(myposIntent, requestCode);

    }

    /**
     * Cancel a preauthorization
     *
     * @param activity    the activity whose context will be used to start the payment activity
     * @param preauth     a {@link MyPOSPreauthorizationCancellation} object with transaction-related data
     * @param requestCode the request code used later to distinguish the type of transaction that has completed
     */
    public static void cancelPreauthorization(Activity activity, MyPOSPreauthorizationCancellation preauth, int requestCode) {
        cancelPreauthorization(activity, preauth, requestCode, false, ReceiptPrintMode.AUTOMATICALLY);
    }

    /**
     * Cancel a preauthorization
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param preauth                a {@link MyPOSPreauthorizationCancellation} object with transaction-related data
     * @param requestCode            the request code used later to distinguish the type of transaction that has completed
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     */
    public static void cancelPreauthorization(Activity activity, MyPOSPreauthorizationCancellation preauth, int requestCode, boolean skipConfirmationScreen) {
        cancelPreauthorization(activity, preauth, requestCode, skipConfirmationScreen, ReceiptPrintMode.AUTOMATICALLY);
    }

    /**
     * Cancel a preauthorization
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param preauth                a {@link MyPOSPreauthorizationCancellation} object with transaction-related data
     * @param requestCode            the request code used later to distinguish the type of transaction that has completed
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     * @param printMode              tells the payment app how receipts should be printed, if any
     */
    public static void cancelPreauthorization(Activity activity, MyPOSPreauthorizationCancellation preauth, int requestCode, boolean skipConfirmationScreen, ReceiptPrintMode printMode) {
        Intent myposIntent;
        if (preauth.isMotoTransaction()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_MOTO_INTENT);
        } else {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_INTENT);
        }

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_PREAUTH_CANCELLATION);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSFER_PREAUTH_CODE, preauth.getPreauthorizationCode());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_RECEIPT_PRINT_MODE, printMode.getValue());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, preauth.getForeignTransactionId());

        activity.startActivityForResult(myposIntent, requestCode);

    }

    public static void createPaymentRequest(Activity activity, MyPOSPaymentRequest paymentRequest, int requestCode) {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_PAYMENT_REQUEST);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, paymentRequest.getProductAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, paymentRequest.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_RECIPIENT_GSM, paymentRequest.getGSM());
        myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_RECIPIENT_EMAIL, paymentRequest.getEMail());
        myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_REASON, paymentRequest.getReason());
        myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_RECIPIENT_NAME, paymentRequest.getRecipientName());
        myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_CODE, paymentRequest.getRequestCode());
        myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_EXPIRY_DAYS, paymentRequest.getExpiryDays());

        activity.startActivityForResult(myposIntent, requestCode);

    }
}
