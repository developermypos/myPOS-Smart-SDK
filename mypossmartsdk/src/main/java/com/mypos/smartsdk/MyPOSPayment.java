package com.mypos.smartsdk;


/**
 * Describes a payment
 */
public class MyPOSPayment {
    private double productAmount;
    private String foreignTransactionId;
    private Currency currency;

    private MyPOSPayment(Builder builder) {
        this.productAmount = builder.productAmount;
        this.foreignTransactionId = builder.foreignTransactionId;
        this.currency = builder.currency;
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

    public static final class Builder {
        private Double productAmount;
        private String foreignTransactionId;
        private Currency currency;

        public Builder productAmount(Double productAmount) {
            this.productAmount = productAmount;
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

        public MyPOSPayment build() {
            if(this.productAmount != null && this.productAmount != 0.0D
                    && this.currency != null) {
                return new MyPOSPayment(this);
            } else {
                throw new IllegalArgumentException("Invalid amount");
            }
        }
    }
}
