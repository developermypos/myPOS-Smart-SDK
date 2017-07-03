package com.mypos.smartsdk;


/**
 * Describes a payment
 */
public class MyPOSPayment {
    private double productAmount;
    private String foreignTransactionId;

    private MyPOSPayment(Builder builder) {
        this.productAmount = builder.productAmount;
        this.foreignTransactionId = builder.foreignTransactionId;
    }


    public static MyPOSPayment.Builder builder() {
        return new MyPOSPayment.Builder();
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

    public static final class Builder {
        private Double productAmount;
        private String foreignTransactionId;

        public Builder productAmount(Double productAmount) {
            this.productAmount = productAmount;
            return this;
        }

        public Builder foreignTransactionId(String foreignTransactionId) {
            this.foreignTransactionId = foreignTransactionId;
            return this;
        }

        public MyPOSPayment build() {
            if(this.productAmount != null && this.productAmount != 0.0D) {
                return new MyPOSPayment(this);
            } else {
                throw new IllegalArgumentException("Invalid amount");
            }
        }
    }
}
