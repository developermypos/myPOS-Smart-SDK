package com.mypos.smartsdk;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;

import com.mypos.smartsdk.data.POSInfo;
import com.mypos.smartsdk.exceptions.FunctionalityNotSupportedException;

import java.util.List;
import java.util.Locale;


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

        final Uri CONTENT_URI = Uri.parse("content://com.mypos.providers.POSInfoProvider/pos_info");

        Cursor cursor = context.getContentResolver().query(
                CONTENT_URI,
                new String[] {"tid", "CurrencyName", "CurrencyCode", "MerchantData"},
                null,
                null,
                null
        );

        if(cursor == null || cursor.getCount() < 1) {
            sendExplicitBroadcast(context, new Intent(MyPOSUtil.GET_SIMPLE_POS_INFO));

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

            return;
        }
        else {
            cursor.moveToFirst();

            POSInfo inf = new POSInfo();
            inf.parseFromCursor(cursor);
            listener.onReceive(inf);
        }

        if(!cursor.isClosed())
            cursor.close();
    }

    public static void sendExplicitBroadcast(Context context, Intent intent) {
        try{
            PackageManager packageManager = context.getPackageManager();
            List<ResolveInfo> resolveInfoList = packageManager.queryBroadcastReceivers(intent, 0);
            for (ResolveInfo info : resolveInfoList) {
                ComponentName cn = new ComponentName(info.activityInfo.packageName, info.activityInfo.name);
                intent.setComponent(cn);
                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                context.sendBroadcast(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
        Intent myposIntent = MyPOSIntents.getPaymentIntent(payment, skipConfirmationScreen);
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
        myposIntent.putExtra(MyPOSUtil.INTENT_FIXED_PINPAD, payment.getFixedPinpad());

        if (payment.getBaseColor() != 0)
            myposIntent.putExtra(MyPOSUtil.INTENT_APP_MAIN_COLOR, payment.getBaseColor());

        startActivityForResult(activity, myposIntent, requestCode);
    }

    /**
     * Takes care of building the intent and opening the vending payment activity
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param vendingPayment         a {@link MyPOSVendingPayment} object with payment-related data
     * @param requestCode            the request code used later to distinguish
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     */
    public static void openVendingPaymentActivity(Activity activity, MyPOSVendingPayment vendingPayment, int requestCode, boolean skipConfirmationScreen) throws FunctionalityNotSupportedException {
        Intent myposIntent = MyPOSIntents.getVendingPaymentIntent(vendingPayment, skipConfirmationScreen);
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
        Intent myposIntent = MyPOSIntents.getRefundIntent(refund, skipConfirmationScreen);
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
        myposIntent.putExtra(MyPOSUtil.INTENT_FIXED_PINPAD, refund.getFixedPinpad());

        if (refund.getBaseColor() != 0)
            myposIntent.putExtra(MyPOSUtil.INTENT_APP_MAIN_COLOR, refund.getBaseColor());

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
        Intent myposIntent = MyPOSIntents.getVoidIntent(voidTr, skipConfirmationScreen);
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
        Intent myposIntent = MyPOSIntents.getPreauthorizationIntent(preauth, skipConfirmationScreen);
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
        myposIntent.putExtra(MyPOSUtil.INTENT_FIXED_PINPAD, preauth.getFixedPinpad());

        if (preauth.getBaseColor() != 0)
            myposIntent.putExtra(MyPOSUtil.INTENT_APP_MAIN_COLOR, preauth.getBaseColor());

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
        Intent myposIntent = MyPOSIntents.getPreauthorizationCompletionIntent(preauth, skipConfirmationScreen);
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

        if (preauth.getBaseColor() != 0)
            myposIntent.putExtra(MyPOSUtil.INTENT_APP_MAIN_COLOR, preauth.getBaseColor());

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
        Intent myposIntent = MyPOSIntents.getPreauthorizationCancellationIntent(preauth, skipConfirmationScreen);
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

        if (preauth.getBaseColor() != 0)
            myposIntent.putExtra(MyPOSUtil.INTENT_APP_MAIN_COLOR, preauth.getBaseColor());

        startActivityForResult(activity, myposIntent, requestCode);

    }

    /**
     * Takes care of building the intent and opening the payment activity
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param activation             a {@link MyPOSGiftCardActivation} object with payment-related data
     * @param requestCode            the request code used later to distinguish
     * @param skipConfirmationScreen if true, the transaction will complete without the confirmation screen showing
     */
    public static void openGiftCardActivationActivity(Activity activity, MyPOSGiftCardActivation activation, int requestCode, boolean skipConfirmationScreen) throws FunctionalityNotSupportedException {
        Intent myposIntent = MyPOSIntents.getGiftCardActivationIntent(activation, skipConfirmationScreen);
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
        openGiftCardDeactivationActivity(activity, MyPOSBase.builder().foreignTransactionId(foreignTransactionId).printCustomerReceipt(MyPOSUtil.RECEIPT_ON).printMerchantReceipt(MyPOSUtil.RECEIPT_ON).build(), requestCode);
    }

    /**
     * Takes care of building the intent and opening the payment activity
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param base                   a payment-related object
     * @param requestCode            the request code used later to distinguish
     */
    public static void openGiftCardDeactivationActivity(Activity activity, MyPOSBase<?> base, int requestCode) throws FunctionalityNotSupportedException {
        Intent myposIntent =MyPOSIntents.getGiftCardDeactivationIntent(base, true);

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
        openGiftCardCheckBalanceActivity(activity,  MyPOSBase.builder().foreignTransactionId(foreignTransactionId).printCustomerReceipt(MyPOSUtil.RECEIPT_ON).printMerchantReceipt(MyPOSUtil.RECEIPT_ON).build(), requestCode);
    }

    /**
     * Takes care of building the intent and opening the payment activity
     *
     * @param activity               the activity whose context will be used to start the payment activity
     * @param base                   a payment-related object
     * @param requestCode            the request code used later to distinguish
     */
    public static void openGiftCardCheckBalanceActivity(Activity activity, MyPOSBase<?> base, int requestCode) throws FunctionalityNotSupportedException {
        Intent myposIntent = MyPOSIntents.getGiftCardBalanceCheckIntent(base, true);
        startActivityForResult(activity, myposIntent, requestCode);
    }

    public static void createPaymentRequest(Activity activity, MyPOSPaymentRequest paymentRequest, int requestCode) throws FunctionalityNotSupportedException {
        Intent myposIntent = MyPOSIntents.getPaymentRequestIntent(paymentRequest);

        startActivityForResult(activity, myposIntent, requestCode);

    }

    public static void openTwintPaymentActivity(Activity activity, double amount, Currency currency, Locale language, int requestCode) throws FunctionalityNotSupportedException {
        Intent myposIntent = MyPOSIntents.getTwintPaymentIntent(amount, currency, language);

        startActivityForResult(activity, myposIntent, requestCode);

    }

    public static void openTwintRefundActivity(Activity activity, double amount, Currency currency, int requestCode) throws FunctionalityNotSupportedException {
        Intent myposIntent = MyPOSIntents.getTwintRefundIntent(amount, currency);

        startActivityForResult(activity, myposIntent, requestCode);

    }

    public static void openTwintVoidActivity(Activity activity, double amount, Currency currency, String originalReference, int requestCode) throws FunctionalityNotSupportedException {
        Intent myposIntent = MyPOSIntents.getTwintVoidIntent(amount, currency, originalReference);

        startActivityForResult(activity, myposIntent, requestCode);

    }

    public static void openCompleteTxActivity(Activity activity, Double partialAmount, String credential, String foreignTransactionId, Locale language,boolean skipConfirmationScreen, int requestCode) throws FunctionalityNotSupportedException {
        Intent myposIntent = MyPOSIntents.getCompleteTxIntent(partialAmount, credential, foreignTransactionId, language, skipConfirmationScreen);

        startActivityForResult(activity, myposIntent, requestCode);

    }

    public static void openCancelTxActivity(Activity activity, String foreignTransactionId, Locale language, boolean skipConfirmationScreen, int requestCode) throws FunctionalityNotSupportedException {
        Intent myposIntent = MyPOSIntents.getCancelTxIntent(foreignTransactionId, language, skipConfirmationScreen);

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
