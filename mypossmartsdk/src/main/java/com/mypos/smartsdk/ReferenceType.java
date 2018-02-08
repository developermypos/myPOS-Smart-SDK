package com.mypos.smartsdk;

public class ReferenceType {

    public final static int OFF                  = 0;
    public final static int REFERENCE_NUMBER     = 1;
    public final static int INVOICE_ID           = 2;
    public final static int PRODUCT_ID           = 3;
    public final static int RESERVATION_NUMBER   = 4;

    public static boolean isInBound(int i) {
        return i >= OFF && i <= RESERVATION_NUMBER;
    }

    public static boolean isEnabled(int i) {
        return i > OFF && i <= RESERVATION_NUMBER;
    }
}
