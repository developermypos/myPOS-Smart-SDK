package com.mypos.smartsdk;

public class MyPOSUtil {
    /**
     * Used to start a transaction.
     */
    public static final String PAYMENT_CORE_ENTRY_POINT_INTENT           = "com.mypos.transaction.START_TRANSACTION";
    /**
     * Used for starting a MOTO transaction
     */
    public static final String PAYMENT_CORE_ENTRY_POINT_MOTO_INTENT      = "com.mypos.transaction.START_MOTO_TRANSACTION";
    /**
     * Used for starting a MOTO transaction
     */
    public static final String PAYMENT_CORE_ENTRY_POINT_GIFTCARD_INTENT  = "com.mypos.transaction.GIFTCARD_PAYMENT";
    /**
     * Start a void transaction
     */
    public static final String PAYMENT_CORE_VOID_INTENT                  = "com.mypos.transaction.VOID";

    public static final String PAYMENT_CORE_VOID_INTENT_EX               = "com.mypos.transaction.VOID_EX";

    public static final String PAYMENT_CORE_ENTRY_PAYMENT_REQUEST        = "com.mypos.transaction.PAYMENT_REQUEST";

    static final String BLOCKING_TRANSACTION_RESULT                      = "com.mypos.BLOCKING_TRANSACTION_RESULT";
    /**
     * Reprint the last transaction receipt
     */
    public static final String PRINT_LAST_RECEIPT_BROADCAST              = "com.mypos.action.PRINT_LAST_TRANSACTION_RECEIPT";

    public static final String GET_SIMPLE_POS_INFO                       = "com.mypos.action.GET_SIMPLE_POS_INFO";
    public static final String GET_SIMPLE_POS_INFO_RESPONSE              = "com.mypos.broadcast.exported.SIMPLE_POS_INFO_RESPONSE";

    public static final String INTENT_PRINT_MERCHANT_RECEIPT             = "print_merchant_receipt";
    public static final String INTENT_PRINT_CUSTOMER_RECEIPT             = "print_customer_receipt";
    public static final int RECEIPT_ON = 1;
    public static final int RECEIPT_OFF = 2;
    public static final int RECEIPT_AFTER_CONFIRMATION = 3;

    /**
     * Returned by the printing broadcasts
     */
    public static final String PRINTING_DONE_BROADCAST                   = "com.mypos.broadcast.PRINTING_DONE";
    /**
     * Print receipt with some data
     */
    public static final String PRINT_BROADCAST                           = "com.mypos.action.PRINT";
    /**
     * Get current status of printer
     */
    public static final String PRINTER_STATUS_BROADCAST                  = "com.mypos.action.PRINTER_STATUS";
    /**
     * Returned by the printing broadcasts
     */
    public static final String PRINTER_STATUS_RESPONSE_BROADCAST         = "com.mypos.broadcast.PRINTER_STATUS_RESPONSE";
     /**
     * send ping broadcast
     */
    public static final String SEND_PING_BROADCAST                       = "com.mypos.broadcast.PING";
    /**
     * Returned by the ping broadcasts
     */
    public static final String PING_DONE_BROADCAST                       = "com.mypos.broadcast.PING_DONE";
    /**
     * send ping broadcast
     */
    public static final String SET_CDC_BROADCAST                         = "com.mypos.action.SET_CDC";
    /**
     * Returned by the ping broadcasts
     */
    public static final String SET_CDC_RESPONSE                          = "com.mypos.action.SET_CDC_RESPONSE";
	/**
     * Open QR/barcode scanner
     */
    public static final String SCANNER_BROADCAST                         = "com.mypos.action.OPEN_SCANNER";
    public static final String SCANNER_RESULT_BROADCAST                  = "com.mypos.action.SCANNER_RESULT";
    /**
     * Used to pass the transaction amount to the Payment core
     */
    public static final String INTENT_TRANSACTION_AMOUNT                 = "amount";
    /**
     * Used for telling the Payment core what the transaction type is
     */
    public static final String INTENT_TRANSACTION_REQUEST_CODE           = "request_code";
    /**
     * For setting the transaction currency
     */
    public static final String INTENT_TRANSACTION_CURRENCY               = "currency";
    /**
     * If true the payment app won't show the confirmation screen
     */
    public static final String INTENT_SKIP_CONFIRMATION_SCREEN           = "skip_confirmation_screen";
    /**
     * Tells the payment app if receipts should be printed
     */
    public static final String INTENT_TRANSACTION_RECEIPT_PRINT_MODE     = "receipt_print_mode";
    /**
     * Used for passing the foreign transaction ID parameter to the payment activity
     */
    public static final String INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID = "foreign_transaction_id";
    /**
     * Whether or not tips should be enabled
     */
    public static final String INTENT_TRANSFER_TIPS_ENABLED              = "tips_enabled";
    /**
     * Amount of the tip
     */
    public static final String INTENT_TRANSACTION_TIP_AMOUNT             = "tip_amount";

    public static final String INTENT_OPERATOR_CODE              = "operator_code";
    public static final String INTENT_REFERENCE_NUMBER           = "reference_number";
    public static final String INTENT_REFERENCE_NUMBER_TYPE      = "reference_number_type";
    public static final String INTENT_MOTO_PASSWORD              = "moto_password";
    public static final String INTENT_RESULT_SCREEN_TIMEOUT      = "result_screen_timeout";
    public static final String INTENT_IS_FISCAL_DEVICE           = "is_fiscal_device";
    public static final String INTENT_FIXED_PINPAD               = "fixed_pinpad";
    public static final String INTENT_ENABLE_MASTERCARD_SONIC    = "enable_mastercard_sonic";

    /**
     * Code used for completing a preauthorization transaction
     */
    public static final String INTENT_TRANSFER_PREAUTH_CODE              = "preauth_code";

    public static final String INTENT_PAYMENT_REQUEST_RECIPIENT_GSM      = "recipient_gsm";
    public static final String INTENT_PAYMENT_REQUEST_RECIPIENT_EMAIL    = "recipient_email";
    public static final String INTENT_PAYMENT_REQUEST_EXPIRY_DAYS        = "expiry_days";
    public static final String INTENT_PAYMENT_REQUEST_CODE               = "requestCode";
    public static final String INTENT_PAYMENT_REQUEST_REASON             = "reason";
    public static final String INTENT_PAYMENT_REQUEST_RECIPIENT_NAME     = "recipient_name";

    public static final String INTENT_VOID_STAN                            = "STAN";
    public static final String INTENT_VOID_AUTH_CODE                       = "authorization_code";
    public static final String INTENT_VOID_DATE_TIME                       = "date_time";

    static final String INTENT_PAYMENT                                   = "payment";
    static final String INTENT_REFUND                                    = "refund";
    static final String INTENT_VOID                                      = "void";

    /**
     * Request code for a Payment
     */
    public static final int TRANSACTION_TYPE_PAYMENT = 101;
    /**
     * Request code for Void
     */
    public static final int TRANSACTION_TYPE_VOID    = 102;
    /**
     * Request code for a Refund
     */
    public static final int TRANSACTION_TYPE_REFUND  = 103;
    /**
     * Request code for creating a preauthorization request
     */
    public static final int TRANSACTION_TYPE_PREAUTH = 104;
    /**
     * Request code for completing a preauthorization request
     */
    public static final int TRANSACTION_TYPE_PREAUTH_COMPLETION = 105;
    /**
     * Request code for cancelling a preauthorization request
     */
    public static final int TRANSACTION_TYPE_PREAUTH_CANCELLATION = 106;
    /**
     * Request code for giftcard activation
     */
    public static final int TRANSACTION_TYPE_GIFTCARD_ACTIVATION        = 107;
    /**
     * Request code for giftcard deactivation
     */
    public static final int TRANSACTION_TYPE_GIFTCARD_DEACTIVATION      = 108;
    /**
     * Request code for giftcard balance check
     */
    public static final int TRANSACTION_TYPE_GIFTCARD_BALANCE_CHECK     = 109;

    // SAM Card
    public static final String INTENT_SAM_CARD                      = "com.mypos.action.SAM_CARD";
    public static final String SAM_CARD_RESPONSE_BROADCAST          = "com.mypos.broadcast.SAM_CARD";
    public static final String INTENT_SAM_CARD_COMMAND              = "command";
    public static final String INTENT_SAM_CARD_SLOT                 = "slot";
    public static final String INTENT_SAM_CARD_REQUEST              = "request";
    public static final String INTENT_SAM_CARD_RESPONSE             = "response";
    public static final String INTENT_GUID                          = "GUID";
    public static final String INTENT_RECEIPT_BOTTOM_SPACE          = "bottom_space";
    public static final String INTENT_CDC_STATUS                    = "cdc_status";

    public static final String INTENT_SAM_CARD_COMMAND_DETECT       = "detect";
    public static final String INTENT_SAM_CARD_COMMAND_OPEN         = "open";
    public static final String INTENT_SAM_CARD_COMMAND_CLOSE        = "close";
    public static final String INTENT_SAM_CARD_COMMAND_ISOCOMMAND   = "isoCommand";

    //Printer
    public static final String INTENT_PRINT_COMMANDS                = "commands";
    public static final String INTENT_PRINT_STATUS                  = "printer_status";
    public static final String INTENT_PRINT_DATA_FILE_PATH          = "file_path";

    public static final String INTENT_STATUS                        = "status";

    public static boolean isReferenceNumberValid(String referenceNumber) {
        return referenceNumber == null || (referenceNumber.length() <= 20 && referenceNumber.matches("[a-zA-Z0-9\\p{Punct}]+"));
    }

}
