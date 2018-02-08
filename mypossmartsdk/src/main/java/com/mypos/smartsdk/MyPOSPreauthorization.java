package com.mypos.smartsdk;

/**
 * Creates a preauthorization transaction
 */

public class MyPOSPreauthorization {

    private boolean     motoTransaction;
    private double      productAmount;
    private String      foreignTransactionId;
    private Currency    currency;
    private int         printMerchantReceipt;
    private int         printCustomerReceipt;
    private String      referenceNumber;
    private int         referenceType;


    MyPOSPreauthorization(MyPOSPreauthorization.Builder builder) {
        this.productAmount = builder.productAmount;
        this.foreignTransactionId = builder.foreignTransactionId;
        this.currency = builder.currency;
        this.motoTransaction = builder.motoTransaction;
        this.printMerchantReceipt = builder.printMerchantReceipt;
        this.printCustomerReceipt = builder.printCustomerReceipt;
        this.referenceNumber = builder.referenceNumber;
        this.referenceType = builder.referenceType;
    }


    public static MyPOSPreauthorization.Builder builder() {
        return new MyPOSPreauthorization.Builder();
    }

    public double getProductAmount() {
        return productAmount;
    }

    public MyPOSPreauthorization setProductAmount(double productAmount) {
        this.productAmount = productAmount;
        return this;
    }

    public String getForeignTransactionId() {
        return foreignTransactionId;
    }

    public MyPOSPreauthorization setForeignTransactionId(String foreignTransactionId) {
        this.foreignTransactionId = foreignTransactionId;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public MyPOSPreauthorization setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public boolean isMotoTransaction() {
        return motoTransaction;
    }

    public MyPOSPreauthorization setMotoTransaction(boolean motoTransaction) {
        this.motoTransaction = motoTransaction;
        return this;
    }

    public int getPrintMerchantReceipt() {
        return printMerchantReceipt;
    }

    public MyPOSPreauthorization setPrintMerchantReceipt(int printMerchantReceipt) {
        this.printMerchantReceipt = printMerchantReceipt;
        return this;
    }

    public int getPrintCustomerReceipt() {
        return printCustomerReceipt;
    }

    public MyPOSPreauthorization setPrintCustomerReceipt(int printCustomerReceipt) {
        this.printCustomerReceipt = printCustomerReceipt;
        return this;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public int getReferenceType() {
        return referenceType;
    }

    public MyPOSPreauthorization setReference(String referenceNumber, int referenceType) {
        this.referenceNumber = referenceNumber;
        this.referenceType = referenceType;
        return this;
    }

    public static class Builder {
        private boolean     motoTransaction;
        private Double      productAmount;
        private String      foreignTransactionId;
        private Currency    currency;
        private int         printMerchantReceipt;
        private int         printCustomerReceipt;
        private String      referenceNumber;
        private int         referenceType;

        public MyPOSPreauthorization.Builder productAmount(Double productAmount) {
            this.productAmount = productAmount;
            return this;
        }

        public MyPOSPreauthorization.Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public MyPOSPreauthorization.Builder foreignTransactionId(String foreignTransactionId) {
            this.foreignTransactionId = foreignTransactionId;
            return this;
        }

        public MyPOSPreauthorization.Builder motoTransaction(boolean motoTransaction) {
            this.motoTransaction = motoTransaction;
            return this;
        }

        public MyPOSPreauthorization.Builder printMerchantReceipt(int printMerchantReceipt) {
            this.printMerchantReceipt = printMerchantReceipt;
            return this;
        }

        public MyPOSPreauthorization.Builder printCustomerReceipt(int printCustomerReceipt) {
            this.printCustomerReceipt = printCustomerReceipt;
            return this;
        }

        public MyPOSPreauthorization.Builder reference(String referenceNumber, int referenceType) {
            this.referenceNumber = referenceNumber;
            this.referenceType = referenceType;
            return this;
        }

        public MyPOSPreauthorization build() {
            if (this.productAmount == null || this.productAmount <= 0.0D) {
                throw new IllegalArgumentException("Invalid or missing amount");
            }
            if (this.currency == null) {
                throw new IllegalArgumentException("Missing currency");
            }
            if(!ReferenceType.isInBound(referenceType)) {
                throw new IllegalArgumentException("reference type out of bound");
            }
            if(ReferenceType.isEnabled(referenceType) && (referenceNumber == null || referenceNumber.length() > 20 || referenceNumber.isEmpty())) {
                throw new IllegalArgumentException("incorrect reference number");
            }

            return new MyPOSPreauthorization(this);
        }
    }


}
