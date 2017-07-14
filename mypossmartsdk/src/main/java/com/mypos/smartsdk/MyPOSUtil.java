package com.mypos.smartsdk;

public class MyPOSUtil {
    /**
     * Used to start a transaction.
     */
    public static final String PAYMENT_CORE_ENTRY_POINT_INTENT = "com.mypos.transaction.START_TRANSACTION";
    /**
     * Start a void transaction
     */
    public static final String PAYMENT_CORE_VOID_INTENT        = "com.mypos.transaction.VOID";
    /**
     * Reprint the last transaction receipt
     */
    public static final String PRINT_LAST_RECEIPT_BROADCAST    = "com.mypos.action.PRINT_LAST_TRANSACTION_RECEIPT";
    /**
     * Returned by the printing broadcasts
     */
    public static final String PRINTING_DONE_BROADCAST         = "com.mypos.broadcast.PRINTING_DONE";
    /**
     * Print receipt with some data
     */
    public static final String PRINT_BROADCAST                 = "com.mypos.action.PRINT";

    /**
     * Used to pass the transaction amount to the Payment core
     */
    public static final String INTENT_TRANSACTION_AMOUNT       = "amount";
    /**
     * Used for telling the Payment core what the transaction type is
     */
    public static final String INTENT_TRANSACTION_REQUEST_CODE        = "request_code";
    /**
     * For setting the transaction currency
     */
    public static final String INTENT_TRANSACTION_CURRENCY        = "currency";
    /**
     * If true the payment app won't show the confirmation screen
     */
    public static final String INTENT_SKIP_CONFIRMATION_SCREEN = "skip_confirmation_screen";
    /**
     * Tells the payment app if receipts should be printed
     */
    public static final String INTENT_TRANSACTION_RECEIPT_PRINT_MODE = "receipt_print_mode";
    /**
     * Used for passing the foreign transaction ID parameter to the payment activity
     */
    public static final String INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID = "foreign_transaction_id";
    /**
     * Request code for a Payment
     */
    public static final int    TRANSACTION_TYPE_PAYMENT               = 101;
    /**
     * Request code for Void
     */
    public static final int    TRANSACTION_TYPE_VOID                  = 102;
    /**
     * Request code for a Refund
     */
    public static final int    TRANSACTION_TYPE_REFUND                = 103;
}
