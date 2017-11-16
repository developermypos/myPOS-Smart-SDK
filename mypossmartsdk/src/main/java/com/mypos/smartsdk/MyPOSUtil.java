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
     * Start a void transaction
     */
    public static final String PAYMENT_CORE_VOID_INTENT                  = "com.mypos.transaction.VOID";

    public static final String PAYMENT_CORE_ENTRY_PAYMENT_REQUEST        = "com.mypos.transaction.PAYMENT_REQUEST";

    /**
     * Reprint the last transaction receipt
     */
    public static final String PRINT_LAST_RECEIPT_BROADCAST              = "com.mypos.action.PRINT_LAST_TRANSACTION_RECEIPT";
    /**
     * Returned by the printing broadcasts
     */
    public static final String PRINTING_DONE_BROADCAST                   = "com.mypos.broadcast.PRINTING_DONE";
    /**
     * Print receipt with some data
     */
    public static final String PRINT_BROADCAST                           = "com.mypos.action.PRINT";
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
    /**
     * Code used for completing a preauthorization transaction
     */
    public static final String INTENT_TRANSFER_PREAUTH_CODE              = "preauth_code";

    public static final String INTENT_PAYMENT_REQUEST_RECIPIENT_GSM      = "recipient_gsm";
    public static final String INTENT_PAYMENT_REQUEST_RECIPIENT_EMAIL    = "recipient_email";
    public static final String INTENT_PAYMENT_REQUEST_EXPIRY_DAYS        = "expiry_days";
    public static final String INTENT_PAYMENT_REQUEST_REASON             = "reason";
    public static final String INTENT_PAYMENT_REQUEST_RECIPIENT_NAME     = "recipient_name";

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

    // SAM Card
    public static final String INTENT_SAM_CARD                      = "com.mypos.action.SAM_CARD";
    public static final String SAM_CARD_RESPONSE_BROADCAST          = "com.mypos.broadcast.SAM_CARD";
    public static final String INTENT_SAM_CARD_COMMAND              = "command";
    public static final String INTENT_SAM_CARD_SLOT                 = "slot";
    public static final String INTENT_SAM_CARD_REQUEST              = "request";
    public static final String INTENT_SAM_CARD_RESPONSE             = "response";

    public static final String INTENT_SAM_CARD_COMMAND_DETECT       = "detect";
    public static final String INTENT_SAM_CARD_COMMAND_OPEN         = "open";
    public static final String INTENT_SAM_CARD_COMMAND_CLOSE        = "close";
    public static final String INTENT_SAM_CARD_COMMAND_ISOCOMMAND   = "isoCommand";

}
