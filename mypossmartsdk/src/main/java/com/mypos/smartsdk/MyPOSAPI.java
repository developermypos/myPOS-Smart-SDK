package com.mypos.smartsdk;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.mypos.smartsdk.data.POSInfo;
import com.mypos.smartsdk.exceptions.FunctionalityNotSupportedException;


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
    public static void openPaymentActivity(Activity activity, MyPOSPayment payment, int requestCode) throws FunctionalityNotSupportedException {
        openPaymentActivity(activity, payment, requestCode, false);
    }

    /**
     * Takes care of building the intent and opening the payment activity
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param payment                a {@link MyPOSPayment} object with payment-related data
     * @param requestCode            the request code used later to distinguish
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     */
    public static void openPaymentActivity(Activity activity, MyPOSPayment payment, int requestCode, boolean skipConfirmationScreen) throws FunctionalityNotSupportedException {
        Intent myposIntent;
        if (payment.isMotoTransaction()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_MOTO_INTENT);
        } else if (payment.isGiftCardTransaction()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_GIFTCARD_INTENT);
        }else {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_INTENT);
        }
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_PAYMENT);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, payment.getProductAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_TIP_AMOUNT, payment.getTipAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSFER_TIPS_ENABLED, payment.isTippingModeEnabled());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, payment.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, payment.getForeignTransactionId());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, payment.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, payment.getPrintCustomerReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_OPERATOR_CODE, payment.getOperatorCode());
        myposIntent.putExtra(MyPOSUtil.INTENT_REFERENCE_NUMBER, payment.getReferenceNumber());
        myposIntent.putExtra(MyPOSUtil.INTENT_REFERENCE_NUMBER_TYPE, payment.getReferenceType());
        myposIntent.putExtra(MyPOSUtil.INTENT_MOTO_PASSWORD, payment.getMotoPassword());

        startActivityForResult(activity, myposIntent, requestCode);
    }

    /**
     * Takes care of building the intent and opening the payment activity
     * @deprecated  printMode param.
     *              {will be removed in 1.0.3 version}
     *              use {@link #openPaymentActivity(Activity activity, MyPOSPayment payment, int requestCode, boolean skipConfirmationScreen)} and printMerchantReceipt / printCustomerReceipt properties in MyPOSPayment Builder.
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param payment                a {@link MyPOSPayment} object with payment-related data
     * @param requestCode            the request code used later to distinguish
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     * @param printMode              tells the payment app how receipts should be printed, if any
     */
    public static void openPaymentActivity(Activity activity, MyPOSPayment payment, int requestCode, boolean skipConfirmationScreen, ReceiptPrintMode printMode) throws FunctionalityNotSupportedException {
        Intent myposIntent;
        if (payment.isMotoTransaction()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_MOTO_INTENT);
        } else if (payment.isGiftCardTransaction()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_GIFTCARD_INTENT);
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
        myposIntent.putExtra(MyPOSUtil.INTENT_OPERATOR_CODE, payment.getOperatorCode());
        myposIntent.putExtra(MyPOSUtil.INTENT_REFERENCE_NUMBER, payment.getReferenceNumber());
        myposIntent.putExtra(MyPOSUtil.INTENT_REFERENCE_NUMBER_TYPE, payment.getReferenceType());
        myposIntent.putExtra(MyPOSUtil.INTENT_MOTO_PASSWORD, payment.getMotoPassword());

        startActivityForResult(activity, myposIntent, requestCode);
    }

    /**
     * Takes care of building the intent and opening the payment activity for a refund transaction
     *
     * @param activity    the activity whose context will be used to start the payment activity
     * @param refund      a {@link MyPOSPayment} object with payment-related data
     * @param requestCode the request code used later to distinguish the type of transaction that has completed
     */
    public static void openRefundActivity(Activity activity, MyPOSRefund refund, int requestCode) throws FunctionalityNotSupportedException {
        openRefundActivity(activity, refund, requestCode, false);
    }

    /**
     * Takes care of building the intent and opening the payment activity for a refund transaction
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param refund                 a {@link MyPOSPayment} object with payment-related data
     * @param requestCode            the request code used later to distinguish the type of transaction that has completed
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     */
    public static void openRefundActivity(Activity activity, MyPOSRefund refund, int requestCode, boolean skipConfirmationScreen) throws FunctionalityNotSupportedException {
        Intent myposIntent;
        if (refund.isMotoTransaction()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_MOTO_INTENT);
        } else if (refund.isGiftCardTransaction()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_GIFTCARD_INTENT);
        } else {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_INTENT);
        }
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_REFUND);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, refund.getRefundAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, refund.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, refund.getForeignTransactionId());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, refund.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, refund.getPrintCustomerReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_MOTO_PASSWORD, refund.getMotoPassword());

        startActivityForResult(activity, myposIntent, requestCode);
    }

    /**
     * Takes care of building the intent and opening the payment activity for a refund transaction
     * @deprecated  printMode param.
     *              {will be removed in 1.0.3 version}
     *              use {@link #openRefundActivity(Activity activity, MyPOSRefund refund, int requestCode, boolean skipConfirmationScreen)} and printMerchantReceipt / printCustomerReceipt properties in MyPOSRefund Builder.
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param refund                 a {@link MyPOSPayment} object with payment-related data
     * @param requestCode            the request code used later to distinguish the type of transaction that has completed
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     * @param printMode              tells the payment app how receipts should be printed, if any
     */
    public static void openRefundActivity(Activity activity, MyPOSRefund refund, int requestCode, boolean skipConfirmationScreen, ReceiptPrintMode printMode) throws FunctionalityNotSupportedException {
        Intent myposIntent;
        if (refund.isMotoTransaction()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_MOTO_INTENT);
        } else if (refund.isGiftCardTransaction()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_GIFTCARD_INTENT);
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
        myposIntent.putExtra(MyPOSUtil.INTENT_MOTO_PASSWORD, refund.getMotoPassword());

        startActivityForResult(activity, myposIntent, requestCode);
    }

    /**
     * Takes care of building the intent and opening the payment activity for a void transaction
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param voidTr                 a {@link MyPOSPayment} object with payment-related data
     * @param requestCode            the request code used later to distinguish the type of transaction that has completed
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     */
    public static void openVoidActivity(Activity activity, MyPOSVoid voidTr, int requestCode, boolean skipConfirmationScreen) throws FunctionalityNotSupportedException {
        Intent myposIntent;
        if(voidTr.getVoidLastTransactionFlag()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_VOID_INTENT);
        } else {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_VOID_INTENT_EX);

            myposIntent.putExtra(MyPOSUtil.INTENT_VOID_STAN, voidTr.getSTAN());
            myposIntent.putExtra(MyPOSUtil.INTENT_VOID_AUTH_CODE, voidTr.getAuthCode());
            myposIntent.putExtra(MyPOSUtil.INTENT_VOID_DATE_TIME, voidTr.getDateTime());
        }
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_VOID);
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, voidTr.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, voidTr.getPrintCustomerReceipt());

        startActivityForResult(activity, myposIntent, requestCode);
    }

    /**
     * Creates a preauthorization request
     *
     * @param activity    the activity whose context will be used to start the payment activity
     * @param preauth     a {@link MyPOSPreauthorization} with transaction-related data
     * @param requestCode the request code used later to distinguish the type of transaction that has completed
     */
    public static void createPreauthorization(Activity activity, MyPOSPreauthorization preauth, int requestCode) throws FunctionalityNotSupportedException {
        createPreauthorization(activity, preauth, requestCode, false);
    }

    /**
     * Creates a preauthorization request
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param preauth                a {@link MyPOSPreauthorization} with transaction-related data
     * @param requestCode            the request code used later to distinguish the type of transaction that has completed
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     */
    public static void createPreauthorization(Activity activity, MyPOSPreauthorization preauth, int requestCode, boolean skipConfirmationScreen) throws FunctionalityNotSupportedException {
        Intent myposIntent;
        if (preauth.isMotoTransaction()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_MOTO_INTENT);
        } else {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_INTENT);
        }

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_PREAUTH);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, preauth.getProductAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, preauth.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, preauth.getForeignTransactionId());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, preauth.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, preauth.getPrintCustomerReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_REFERENCE_NUMBER, preauth.getReferenceNumber());
        myposIntent.putExtra(MyPOSUtil.INTENT_REFERENCE_NUMBER_TYPE, preauth.getReferenceType());
        myposIntent.putExtra(MyPOSUtil.INTENT_MOTO_PASSWORD, preauth.getMotoPassword());

        startActivityForResult(activity, myposIntent, requestCode);
    }

    /**
     * Create a preauthorization request
     *
     * @deprecated  printMode param.
     *              {will be removed in 1.0.3 version}
     *              use {@link #createPreauthorization(Activity activity, MyPOSPreauthorization preauth, int requestCode, boolean skipConfirmationScreen)} and printMerchantReceipt / printCustomerReceipt properties in MyPOSPreauthorization Builder.
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param preauth                a {@link MyPOSPreauthorization} with transaction-related data
     * @param requestCode            the request code used later to distinguish the type of transaction that has completed
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     * @param printMode              tells the payment app how receipts should be printed, if any
     */
    public static void createPreauthorization(Activity activity, MyPOSPreauthorization preauth, int requestCode, boolean skipConfirmationScreen, ReceiptPrintMode printMode) throws FunctionalityNotSupportedException {
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
        myposIntent.putExtra(MyPOSUtil.INTENT_REFERENCE_NUMBER, preauth.getReferenceNumber());
        myposIntent.putExtra(MyPOSUtil.INTENT_REFERENCE_NUMBER_TYPE, preauth.getReferenceType());
        myposIntent.putExtra(MyPOSUtil.INTENT_MOTO_PASSWORD, preauth.getMotoPassword());

        startActivityForResult(activity, myposIntent, requestCode);
    }

    /**
     * Complete a preauthorization
     *
     * @param activity    the activity whose context will be used to start the payment activity
     * @param preauth     a {@link MyPOSPreauthorizationCompletion} object with transaction-related data
     * @param requestCode the request code used later to distinguish the type of transaction that has completed
     */
    public static void completePreauthorization(Activity activity, MyPOSPreauthorizationCompletion preauth, int requestCode) throws FunctionalityNotSupportedException {
        completePreauthorization(activity, preauth, requestCode, false);
    }

    /**
     * Complete a preauthorization
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param preauth                a {@link MyPOSPreauthorizationCompletion} object with transaction-related data
     * @param requestCode            the request code used later to distinguish the type of transaction that has completed
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     */
    public static void completePreauthorization(Activity activity, MyPOSPreauthorizationCompletion preauth, int requestCode, boolean skipConfirmationScreen) throws FunctionalityNotSupportedException {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_INTENT);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_PREAUTH_COMPLETION);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSFER_PREAUTH_CODE, preauth.getPreauthorizationCode());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, preauth.getProductAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, preauth.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, preauth.getForeignTransactionId());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, preauth.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, preauth.getPrintCustomerReceipt());

        startActivityForResult(activity, myposIntent, requestCode);
    }

    /**
     * Complete a preauthorization
     * @deprecated  printMode param.
     *              {will be removed in 1.0.3 version}
     *              use {@link #completePreauthorization(Activity activity, MyPOSPreauthorizationCompletion preauth, int requestCode, boolean skipConfirmationScreen)} and printMerchantReceipt / printCustomerReceipt properties in MyPOSPreauthorizationCompletion Builder.
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param preauth                a {@link MyPOSPreauthorizationCompletion} object with transaction-related data
     * @param requestCode            the request code used later to distinguish the type of transaction that has completed
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     * @param printMode              tells the payment app how receipts should be printed, if any
     */
    public static void completePreauthorization(Activity activity, MyPOSPreauthorizationCompletion preauth, int requestCode, boolean skipConfirmationScreen, ReceiptPrintMode printMode) throws FunctionalityNotSupportedException {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_INTENT);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_PREAUTH_COMPLETION);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSFER_PREAUTH_CODE, preauth.getPreauthorizationCode());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, preauth.getProductAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_RECEIPT_PRINT_MODE, printMode.getValue());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, preauth.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, preauth.getForeignTransactionId());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, preauth.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, preauth.getPrintCustomerReceipt());

        startActivityForResult(activity, myposIntent, requestCode);

    }

    /**
     * Cancel a preauthorization
     *
     * @param activity    the activity whose context will be used to start the payment activity
     * @param preauth     a {@link MyPOSPreauthorizationCancellation} object with transaction-related data
     * @param requestCode the request code used later to distinguish the type of transaction that has completed
     */
    public static void cancelPreauthorization(Activity activity, MyPOSPreauthorizationCancellation preauth, int requestCode) throws FunctionalityNotSupportedException {
        cancelPreauthorization(activity, preauth, requestCode, false);
    }

    /**
     * Cancel a preauthorization
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param preauth                a {@link MyPOSPreauthorizationCancellation} object with transaction-related data
     * @param requestCode            the request code used later to distinguish the type of transaction that has completed
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     */
    public static void cancelPreauthorization(Activity activity, MyPOSPreauthorizationCancellation preauth, int requestCode, boolean skipConfirmationScreen) throws FunctionalityNotSupportedException {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_INTENT);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_PREAUTH_CANCELLATION);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSFER_PREAUTH_CODE, preauth.getPreauthorizationCode());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, preauth.getForeignTransactionId());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, preauth.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, preauth.getPrintCustomerReceipt());

        startActivityForResult(activity, myposIntent, requestCode);
    }

    /**
     * Cancel a preauthorization
     *@deprecated  printMode param.
     *              {will be removed in 1.0.3 version}
     *              use {@link #cancelPreauthorization(Activity activity, MyPOSPreauthorizationCancellation preauth, int requestCode, boolean skipConfirmationScreen)} and printMerchantReceipt / printCustomerReceipt properties in MyPOSPreauthorizationCancellation Builder.
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param preauth                a {@link MyPOSPreauthorizationCancellation} object with transaction-related data
     * @param requestCode            the request code used later to distinguish the type of transaction that has completed
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     * @param printMode              tells the payment app how receipts should be printed, if any
     */
    public static void cancelPreauthorization(Activity activity, MyPOSPreauthorizationCancellation preauth, int requestCode, boolean skipConfirmationScreen, ReceiptPrintMode printMode) throws FunctionalityNotSupportedException {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_INTENT);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_PREAUTH_CANCELLATION);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSFER_PREAUTH_CODE, preauth.getPreauthorizationCode());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_RECEIPT_PRINT_MODE, printMode.getValue());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, preauth.getForeignTransactionId());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, preauth.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, preauth.getPrintCustomerReceipt());

        startActivityForResult(activity, myposIntent, requestCode);

    }

    /**
     * Takes care of building the intent and opening the payment activity
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param activation             a {@link MyPOSPayment} object with payment-related data
     * @param requestCode            the request code used later to distinguish
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     */
    public static void openGiftCardActivationActivity(Activity activity, MyPOSGiftCardActivation activation, int requestCode, boolean skipConfirmationScreen) throws FunctionalityNotSupportedException {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_GIFTCARD_INTENT);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_GIFTCARD_ACTIVATION);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, activation.getProductAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, activation.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, activation.getForeignTransactionId());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, activation.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, activation.getPrintCustomerReceipt());

        startActivityForResult(activity, myposIntent, requestCode);
    }

    /**
     * Takes care of building the intent and opening the payment activity
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param foreignTransactionId   a payment-related id
     * @param requestCode            the request code used later to distinguish
     */
    public static void openGiftCardDeactivationActivity(Activity activity, String foreignTransactionId, int requestCode) throws FunctionalityNotSupportedException {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_GIFTCARD_INTENT);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_GIFTCARD_DEACTIVATION);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, foreignTransactionId);

        startActivityForResult(activity, myposIntent, requestCode);
    }

    /**
     * Takes care of building the intent and opening the payment activity
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param foreignTransactionId   a payment-related id
     * @param requestCode            the request code used later to distinguish
     */
    public static void openGiftCardCheckBalanceActivity(Activity activity, String foreignTransactionId, int requestCode) throws FunctionalityNotSupportedException {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_GIFTCARD_INTENT);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_GIFTCARD_BALANCE_CHECK);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, foreignTransactionId);

        startActivityForResult(activity, myposIntent, requestCode);
    }

    public static void createPaymentRequest(Activity activity, MyPOSPaymentRequest paymentRequest, int requestCode) throws FunctionalityNotSupportedException {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_PAYMENT_REQUEST);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, paymentRequest.getProductAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, paymentRequest.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_RECIPIENT_GSM, paymentRequest.getGSM());
        myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_RECIPIENT_EMAIL, paymentRequest.getEMail());
        myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_REASON, paymentRequest.getReason());
        myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_RECIPIENT_NAME, paymentRequest.getRecipientName());
        myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_CODE, paymentRequest.getRequestCode());
        myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_EXPIRY_DAYS, paymentRequest.getExpiryDays());

        startActivityForResult(activity, myposIntent, requestCode);

    }

    private static void startActivityForResult(Activity activity, Intent intent, int requestCode) throws FunctionalityNotSupportedException {
        try {
            activity.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            throw new FunctionalityNotSupportedException("Functionality not supported");
        }
    }
}
