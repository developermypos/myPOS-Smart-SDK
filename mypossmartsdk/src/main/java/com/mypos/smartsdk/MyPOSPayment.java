package com.mypos.smartsdk;


/**
 * Describes a payment
 */
public class MyPOSPayment {

    private boolean     tippingModeEnabled;
    private boolean     motoTransaction;
    private double      productAmount;
    private double      tipAmount;
    private String      foreignTransactionId;
    private Currency    currency;
    private int         printMerchantReceipt;
    private int         printCustomerReceipt;


    MyPOSPayment(Builder builder) {
        this.productAmount = builder.productAmount;
        this.foreignTransactionId = builder.foreignTransactionId;
        this.currency = builder.currency;
        this.tippingModeEnabled = builder.tippingModeEnabled;
        this.tipAmount = builder.tipAmount;
        this.motoTransaction = builder.motoTransaction;
        this.printMerchantReceipt = builder.printMerchantReceipt;
        this.printCustomerReceipt = builder.printCustomerReceipt;
    }


    public static Builder builder() {
        return new Builder();
    }

    public double getProductAmount() {
        return productAmount;
    }

    public MyPOSPayment setProductAmount(double productAmount) {
        this.productAmount = productAmount;
        return this;
    }

    public String getForeignTransactionId() {
        return foreignTransactionId;
    }

    public MyPOSPayment setForeignTransactionId(String foreignTransactionId) {
        this.foreignTransactionId = foreignTransactionId;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public MyPOSPayment setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public boolean isTippingModeEnabled() {
        return tippingModeEnabled;
    }

    public MyPOSPayment setTippingModeEnabled(boolean tippingModeEnabled) {
        this.tippingModeEnabled = tippingModeEnabled;
        return this;
    }

    public double getTipAmount() {
        return tipAmount;
    }

    public MyPOSPayment setTipAmount(double tipAmount) {
        this.tipAmount = tipAmount;
        return this;
    }

    public boolean isMotoTransaction() {
        return motoTransaction;
    }

    public MyPOSPayment setMotoTransaction(boolean motoTransaction) {
        this.motoTransaction = motoTransaction;
        return this;
    }

    public int getPrintMerchantReceipt() {
        return printMerchantReceipt;
    }

    public MyPOSPayment setPrintMerchantReceipt(int printMerchantReceipt) {
        this.printMerchantReceipt = printMerchantReceipt;
        return this;
    }

    public int getPrintCustomerReceipt() {
        return printCustomerReceipt;
    }

    public MyPOSPayment setPrintCustomerReceipt(int printCustomerReceipt) {
        this.printCustomerReceipt = printCustomerReceipt;
        return this;
    }

    public static class Builder {
        private boolean     tippingModeEnabled;
        private boolean     motoTransaction;
        private double      tipAmount;
        private Double      productAmount;
        private String      foreignTransactionId;
        private Currency    currency;
        private int         printMerchantReceipt;
        private int         printCustomerReceipt;

        public Builder productAmount(Double productAmount) {
            this.productAmount = productAmount;
            return this;
        }

        public Builder tipAmount(Double tipAmount) {
            this.tipAmount = tipAmount;
            return this;
        }

        public Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public Builder foreignTransactionId(String foreignTransactionId) {
            this.foreignTransactionId = foreignTransactionId;
            return this;
        }

        public Builder tippingModeEnabled(boolean tippingModeEnabled) {
            this.tippingModeEnabled = tippingModeEnabled;
            return this;
        }

        public Builder motoTransaction(boolean motoTransaction) {
            this.motoTransaction = motoTransaction;
            return this;
        }

        public Builder printMerchantReceipt(int printMerchantReceipt) {
            this.printMerchantReceipt = printMerchantReceipt;
            return this;
        }

        public Builder printCustomerReceipt(int printCustomerReceipt) {
            this.printCustomerReceipt = printCustomerReceipt;
            return this;
        }

        public MyPOSPayment build() {
            if (this.productAmount == null || this.productAmount <= 0.0D) {
                throw new IllegalArgumentException("Invalid or missing amount");
            }
            if (this.currency == null) {
                throw new IllegalArgumentException("Missing currency");
            }

            if (this.tippingModeEnabled && this.tipAmount <= 0.0D) {
                throw new IllegalArgumentException("Invalid tip amount");
            }

            return new MyPOSPayment(this);
        }
    }
}
