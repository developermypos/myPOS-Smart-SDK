package com.mypos.smartsdk;

/**
 * Cancel a preauthorization
 */
public class MyPOSPreauthorizationCancellation {

    private boolean             motoTransaction;
    private String              foreignTransactionId;
    private String              preauthorizationCode;
    private int                 printMerchantReceipt;
    private int                 printCustomerReceipt;
    private String              referenceNumber;
    private int                 referenceType;


    MyPOSPreauthorizationCancellation(MyPOSPreauthorizationCancellation.Builder builder) {
        this.foreignTransactionId = builder.foreignTransactionId;
        this.motoTransaction = builder.motoTransaction;
        this.preauthorizationCode = builder.preauthorizationCode;
        this.printMerchantReceipt = builder.printMerchantReceipt;
        this.printCustomerReceipt = builder.printCustomerReceipt;
        this.referenceNumber = builder.referenceNumber;
        this.referenceType = builder.referenceType;
    }


    public static MyPOSPreauthorizationCancellation.Builder builder() {
        return new MyPOSPreauthorizationCancellation.Builder();
    }

    public String getForeignTransactionId() {
        return foreignTransactionId;
    }

    public MyPOSPreauthorizationCancellation setForeignTransactionId(String foreignTransactionId) {
        this.foreignTransactionId = foreignTransactionId;
        return this;
    }

    public boolean isMotoTransaction() {
        return motoTransaction;
    }

    public MyPOSPreauthorizationCancellation setMotoTransaction(boolean motoTransaction) {
        this.motoTransaction = motoTransaction;
        return this;
    }

    public String getPreauthorizationCode() {
        return preauthorizationCode;
    }

    public MyPOSPreauthorizationCancellation setPreauthorizationCode(String preauthorizationCode) {
        this.preauthorizationCode = preauthorizationCode;
        return this;
    }

    public int getPrintMerchantReceipt() {
        return printMerchantReceipt;
    }

    public MyPOSPreauthorizationCancellation setPrintMerchantReceipt(int printMerchantReceipt) {
        this.printMerchantReceipt = printMerchantReceipt;
        return this;
    }

    public int getPrintCustomerReceipt() {
        return printCustomerReceipt;
    }

    public MyPOSPreauthorizationCancellation setPrintCustomerReceipt(int printCustomerReceipt) {
        this.printCustomerReceipt = printCustomerReceipt;
        return this;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public int getReferenceType() {
        return referenceType;
    }

    public MyPOSPreauthorizationCancellation setReference(String referenceNumber, int referenceType) {
        this.referenceNumber = referenceNumber;
        this.referenceType = referenceType;
        return this;
    }

    public static class Builder {
        private boolean             motoTransaction;
        private String              foreignTransactionId;
        private String              preauthorizationCode;
        private int                 printMerchantReceipt;
        private int                 printCustomerReceipt;
        private String              referenceNumber;
        private int                 referenceType;

        public MyPOSPreauthorizationCancellation.Builder foreignTransactionId(String foreignTransactionId) {
            this.foreignTransactionId = foreignTransactionId;
            return this;
        }

        public MyPOSPreauthorizationCancellation.Builder motoTransaction(boolean motoTransaction) {
            this.motoTransaction = motoTransaction;
            return this;
        }

        public MyPOSPreauthorizationCancellation.Builder preauthorizationCode(String preauthorizationCode) {
            this.preauthorizationCode = preauthorizationCode;
            return this;
        }

        public MyPOSPreauthorizationCancellation.Builder printMerchantReceipt(int printMerchantReceipt) {
            this.printMerchantReceipt = printMerchantReceipt;
            return this;
        }

        public MyPOSPreauthorizationCancellation.Builder printCustomerReceipt(int printCustomerReceipt) {
            this.printCustomerReceipt = printCustomerReceipt;
            return this;
        }

        public MyPOSPreauthorizationCancellation.Builder reference(String referenceNumber, int referenceType) {
            this.referenceNumber = referenceNumber;
            this.referenceType = referenceType;
            return this;
        }

        public MyPOSPreauthorizationCancellation build() {
            if (this.preauthorizationCode == null || preauthorizationCode.isEmpty()) {
                throw new IllegalArgumentException("Missing preauthorization code");
            }
            if(!ReferenceType.isInBound(referenceType)) {
                throw new IllegalArgumentException("reference type out of bound");
            }
            if(ReferenceType.isEnabled(referenceType) && (referenceNumber == null || referenceNumber.length() > 20 || referenceNumber.isEmpty())) {
                throw new IllegalArgumentException("incorrect reference number");
            }

            return new MyPOSPreauthorizationCancellation(this);
        }
    }




}
