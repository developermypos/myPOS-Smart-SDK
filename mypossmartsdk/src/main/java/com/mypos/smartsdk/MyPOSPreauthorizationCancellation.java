package com.mypos.smartsdk;

/**
 * Cancel a preauthorization
 */
public class MyPOSPreauthorizationCancellation {

    private boolean              motoTransaction;
    private double               productAmount;
    private String               foreignTransactionId;
    private String               preauthorizationCode;
    private Currency             currency;


    MyPOSPreauthorizationCancellation(MyPOSPreauthorizationCancellation.Builder builder) {
        this.productAmount = builder.productAmount;
        this.foreignTransactionId = builder.foreignTransactionId;
        this.currency = builder.currency;
        this.motoTransaction = builder.motoTransaction;
        this.preauthorizationCode = builder.preauthorizationCode;
    }


    public static MyPOSPreauthorizationCancellation.Builder builder() {
        return new MyPOSPreauthorizationCancellation.Builder();
    }

    public double getProductAmount() {
        return productAmount;
    }

    public MyPOSPreauthorizationCancellation setProductAmount(double productAmount) {
        this.productAmount = productAmount;
        return this;
    }

    public String getForeignTransactionId() {
        return foreignTransactionId;
    }

    public MyPOSPreauthorizationCancellation setForeignTransactionId(String foreignTransactionId) {
        this.foreignTransactionId = foreignTransactionId;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public MyPOSPreauthorizationCancellation setCurrency(Currency currency) {
        this.currency = currency;
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

    public static class Builder {
        private boolean              motoTransaction;
        private Double               productAmount;
        private String               foreignTransactionId;
        private Currency             currency;
        private String               preauthorizationCode;

        public MyPOSPreauthorizationCancellation.Builder productAmount(Double productAmount) {
            this.productAmount = productAmount;
            return this;
        }

        public MyPOSPreauthorizationCancellation.Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

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


        public MyPOSPreauthorizationCancellation build() {
            if (this.productAmount == null || this.productAmount <= 0.0D) {
                throw new IllegalArgumentException("Invalid or missing amount");
            }
            if (this.currency == null) {
                throw new IllegalArgumentException("Missing currency");
            }

            if (this.preauthorizationCode == null || preauthorizationCode.isEmpty()) {
                throw new IllegalArgumentException("Missing preauthorization code");
            }

            return new MyPOSPreauthorizationCancellation(this);
        }
    }




}
