package com.mypos.smartsdk;

/**
 * @deprecated
 * Describes how the receipts are printed
 */
public enum ReceiptPrintMode {
    /**
     * No receipt will be printed
     */
    NO_RECEIPT(0),
    /**
     * Customer and merchant receipt are printed automatically if the confirmation screen is not shown.
     */
    AUTOMATICALLY(1),
    /**
     * Only a merchant's receipt is printed when the confirmation screen is not shown
     */
    NO_CUSTOMER_RECEIPT(2);

    private final int value;

    ReceiptPrintMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
