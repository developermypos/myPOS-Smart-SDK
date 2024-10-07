package com.mypos.smartsdk;

public class TransactionProcessingResult {
    /**
     * Transaction completed successfully
     */
    public static final int TRANSACTION_SUCCESS  = 0;
    /**
     * User canceled the transaction
     */
    public static final int TRANSACTION_CANCELED = 1;
    /**
     * The transaction was declined for some reason (by the Host or the Issuer)
     */
    public static final int TRANSACTION_DECLINED = 2;
    /**
     * The transaction failed - because of a connection timeout or some other malfunction
     */
    public static final int TRANSACTION_FAILED   = 3;
    /**
     * The device is not activated, meaning no transactions can be performed
     */
    public static final int DEVICE_NOT_ACTIVATED = 4;
    /**
     * Some needed data was missing. Mainly used when a Void transaction is requested when there is no previous transaction data present
     */
    public static final int NO_DATA_FOUND        = 5;
    /**
     * When a currency different than the device's currency is set when calling the payment app
     */
    public static final int INVALID_CURRENCY     = 6;
    /**
     * When the amount is greater than the allowed maximum or less than the allowed minimum.
     */
    public static final int INVALID_AMOUNT       = 7;
}
