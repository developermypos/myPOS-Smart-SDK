package com.mypos.smartsdk;


/**
 * Describes a refund
 */
public class MyPOSRefund {

    private double               refundAmount;
    private boolean              motoTransaction;
    private String               foreignTransactionId;
    private Currency             currency;

    private MyPOSRefund(Builder builder) {
        this.refundAmount = builder.refundAmount;
        this.foreignTransactionId = builder.foreignTransactionId;
        this.currency = builder.currency;
        this.motoTransaction = builder.motoTransaction;
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

    public static final class Builder {
        private boolean              motoTransaction;
        private Double               refundAmount;
        private String               foreignTransactionId;
        private Currency             currency;
        private String               preauthorizationCode;

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
