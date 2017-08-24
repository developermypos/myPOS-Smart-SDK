package com.mypos.smartsdk;

/**
 * Creates a preauthorization transaction
 */

public class MyPOSPreauthorization {

    private boolean  motoTransaction;
    private double   productAmount;
    private String   foreignTransactionId;
    private Currency currency;


    MyPOSPreauthorization(MyPOSPreauthorization.Builder builder) {
        this.productAmount = builder.productAmount;
        this.foreignTransactionId = builder.foreignTransactionId;
        this.currency = builder.currency;
        this.motoTransaction = builder.motoTransaction;
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

    public static class Builder {
        private boolean  motoTransaction;
        private Double   productAmount;
        private String   foreignTransactionId;
        private Currency currency;

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


        public MyPOSPreauthorization build() {
            if (this.productAmount == null || this.productAmount <= 0.0D) {
                throw new IllegalArgumentException("Invalid or missing amount");
            }
            if (this.currency == null) {
                throw new IllegalArgumentException("Missing currency");
            }

            return new MyPOSPreauthorization(this);
        }
    }


}
