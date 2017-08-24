package com.mypos.smartsdk;

/**
 * Complete a preauthorization
 */
public class MyPOSPreauthorizationCompletion {

    private boolean              motoTransaction;
    private double               productAmount;
    private String               foreignTransactionId;
    private String               preauthorizationCode;
    private Currency             currency;


    MyPOSPreauthorizationCompletion(MyPOSPreauthorizationCompletion.Builder builder) {
        this.productAmount = builder.productAmount;
        this.foreignTransactionId = builder.foreignTransactionId;
        this.currency = builder.currency;
        this.motoTransaction = builder.motoTransaction;
        this.preauthorizationCode = builder.preauthorizationCode;
    }


    public static MyPOSPreauthorizationCompletion.Builder builder() {
        return new MyPOSPreauthorizationCompletion.Builder();
    }

    public double getProductAmount() {
        return productAmount;
    }

    public MyPOSPreauthorizationCompletion setProductAmount(double productAmount) {
        this.productAmount = productAmount;
        return this;
    }

    public String getForeignTransactionId() {
        return foreignTransactionId;
    }

    public MyPOSPreauthorizationCompletion setForeignTransactionId(String foreignTransactionId) {
        this.foreignTransactionId = foreignTransactionId;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public MyPOSPreauthorizationCompletion setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public boolean isMotoTransaction() {
        return motoTransaction;
    }

    public MyPOSPreauthorizationCompletion setMotoTransaction(boolean motoTransaction) {
        this.motoTransaction = motoTransaction;
        return this;
    }

    public String getPreauthorizationCode() {
        return preauthorizationCode;
    }

    public MyPOSPreauthorizationCompletion setPreauthorizationCode(String preauthorizationCode) {
        this.preauthorizationCode = preauthorizationCode;
        return this;
    }

    public static class Builder {
        private boolean              motoTransaction;
        private Double               productAmount;
        private String               foreignTransactionId;
        private Currency             currency;
        private String               preauthorizationCode;

        public MyPOSPreauthorizationCompletion.Builder productAmount(Double productAmount) {
            this.productAmount = productAmount;
            return this;
        }

        public MyPOSPreauthorizationCompletion.Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public MyPOSPreauthorizationCompletion.Builder foreignTransactionId(String foreignTransactionId) {
            this.foreignTransactionId = foreignTransactionId;
            return this;
        }

        public MyPOSPreauthorizationCompletion.Builder motoTransaction(boolean motoTransaction) {
            this.motoTransaction = motoTransaction;
            return this;
        }

        public MyPOSPreauthorizationCompletion.Builder preauthorizationCode(String preauthorizationCode) {
            this.preauthorizationCode = preauthorizationCode;
            return this;
        }


        public MyPOSPreauthorizationCompletion build() {
            if (this.productAmount == null || this.productAmount <= 0.0D) {
                throw new IllegalArgumentException("Invalid or missing amount");
            }
            if (this.currency == null) {
                throw new IllegalArgumentException("Missing currency");
            }

            if (this.preauthorizationCode == null || preauthorizationCode.isEmpty()) {
                throw new IllegalArgumentException("Missing preauthorization code");
            }

            return new MyPOSPreauthorizationCompletion(this);
        }
    }


}
