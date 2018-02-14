package com.mypos.smartsdk;


/**
 * Describes a refund
 */
public class MyPOSRefund {

    private double              refundAmount;
    private boolean             motoTransaction;
    private String              foreignTransactionId;
    private Currency            currency;
    private int                 printMerchantReceipt;
    private int                 printCustomerReceipt;
    private String              motoPassword;

    private MyPOSRefund(Builder builder) {
        this.refundAmount = builder.refundAmount;
        this.foreignTransactionId = builder.foreignTransactionId;
        this.currency = builder.currency;
        this.motoTransaction = builder.motoTransaction;
        this.printMerchantReceipt = builder.printMerchantReceipt;
        this.printCustomerReceipt = builder.printCustomerReceipt;
        this.motoPassword = builder.motoPassword;
    }


    public static Builder builder() {
        return new Builder();
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public MyPOSRefund setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
        return this;
    }

    public String getForeignTransactionId() {
        return foreignTransactionId;
    }

    public MyPOSRefund setForeignTransactionId(String foreignTransactionId) {
        this.foreignTransactionId = foreignTransactionId;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public MyPOSRefund setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public boolean isMotoTransaction() {
        return motoTransaction;
    }

    public MyPOSRefund setMotoTransaction(boolean motoTransaction) {
        this.motoTransaction = motoTransaction;
        return this;
    }

    public String getMotoPassword() {
        return motoPassword;
    }

    public MyPOSRefund setMotoPassword(String motoPassword) {
        this.motoPassword = motoPassword;
        return this;
    }

    public int getPrintMerchantReceipt() {
        return printMerchantReceipt;
    }

    public MyPOSRefund setPrintMerchantReceipt(int printMerchantReceipt) {
        this.printMerchantReceipt = printMerchantReceipt;
        return this;
    }

    public int getPrintCustomerReceipt() {
        return printCustomerReceipt;
    }

    public MyPOSRefund setPrintCustomerReceipt(int printCustomerReceipt) {
        this.printCustomerReceipt = printCustomerReceipt;
        return this;
    }

    public static final class Builder {
        private boolean         motoTransaction;
        private Double          refundAmount;
        private String          foreignTransactionId;
        private Currency        currency;
        private int             printMerchantReceipt;
        private int             printCustomerReceipt;
        private String          motoPassword;

        public Builder refundAmount(Double productAmount) {
            this.refundAmount = productAmount;
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

        public Builder motoTransaction(boolean motoTransaction) {
            this.motoTransaction = motoTransaction;
            return this;
        }

        public Builder motoPassword(String motoPassword) {
            this.motoPassword = motoPassword;
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

        public MyPOSRefund build() {
            if (this.refundAmount == null || this.refundAmount <= 0.0D) {
                throw new IllegalArgumentException("Invalid amount");
            }
            if (this.currency == null) {
                throw new IllegalArgumentException("Invalid currency");
            }

            return new MyPOSRefund(this);
        }
    }
}
