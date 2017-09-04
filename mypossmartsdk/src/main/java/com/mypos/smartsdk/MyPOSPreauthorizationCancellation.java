package com.mypos.smartsdk;

/**
 * Cancel a preauthorization
 */
public class MyPOSPreauthorizationCancellation {

    private boolean              motoTransaction;
    private String               foreignTransactionId;
    private String               preauthorizationCode;


    MyPOSPreauthorizationCancellation(MyPOSPreauthorizationCancellation.Builder builder) {
        this.foreignTransactionId = builder.foreignTransactionId;
        this.motoTransaction = builder.motoTransaction;
        this.preauthorizationCode = builder.preauthorizationCode;
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

    public static class Builder {
        private boolean              motoTransaction;
        private String               foreignTransactionId;
        private String               preauthorizationCode;

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
            if (this.preauthorizationCode == null || preauthorizationCode.isEmpty()) {
                throw new IllegalArgumentException("Missing preauthorization code");
            }

            return new MyPOSPreauthorizationCancellation(this);
        }
    }




}
