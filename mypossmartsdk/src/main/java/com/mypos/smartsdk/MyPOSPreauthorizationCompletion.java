package com.mypos.smartsdk;

/**
 * Complete a preauthorization
 */
public class MyPOSPreauthorizationCompletion {

    private double              productAmount;
    private String              foreignTransactionId;
    private String              preauthorizationCode;
    private Currency            currency;
    private int                 printMerchantReceipt;
    private int                 printCustomerReceipt;
    private String              referenceNumber;
    private int                 referenceType;

    MyPOSPreauthorizationCompletion(MyPOSPreauthorizationCompletion.Builder builder) {
        this.productAmount = builder.productAmount;
        this.foreignTransactionId = builder.foreignTransactionId;
        this.currency = builder.currency;
        this.preauthorizationCode = builder.preauthorizationCode;
        this.printMerchantReceipt = builder.printMerchantReceipt;
        this.printCustomerReceipt = builder.printCustomerReceipt;
        this.referenceNumber = builder.referenceNumber;
        this.referenceType = builder.referenceType;
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

    public String getPreauthorizationCode() {
        return preauthorizationCode;
    }

    public MyPOSPreauthorizationCompletion setPreauthorizationCode(String preauthorizationCode) {
        this.preauthorizationCode = preauthorizationCode;
        return this;
    }

    public int getPrintMerchantReceipt() {
        return printMerchantReceipt;
    }

    public MyPOSPreauthorizationCompletion setPrintMerchantReceipt(int printMerchantReceipt) {
        this.printMerchantReceipt = printMerchantReceipt;
        return this;
    }

    public int getPrintCustomerReceipt() {
        return printCustomerReceipt;
    }

    public MyPOSPreauthorizationCompletion setPrintCustomerReceipt(int printCustomerReceipt) {
        this.printCustomerReceipt = printCustomerReceipt;
        return this;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public int getReferenceType() {
        return referenceType;
    }

    public MyPOSPreauthorizationCompletion setReference(String referenceNumber, int referenceType) {
        this.referenceNumber = referenceNumber;
        this.referenceType = referenceType;
        return this;
    }

    public static class Builder {
        private Double              productAmount;
        private String              foreignTransactionId;
        private Currency            currency;
        private String              preauthorizationCode;
        private int                 printMerchantReceipt;
        private int                 printCustomerReceipt;
        private String              referenceNumber;
        private int                 referenceType;

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

        public MyPOSPreauthorizationCompletion.Builder preauthorizationCode(String preauthorizationCode) {
            this.preauthorizationCode = preauthorizationCode;
            return this;
        }

        public MyPOSPreauthorizationCompletion.Builder printMerchantReceipt(int printMerchantReceipt) {
            this.printMerchantReceipt = printMerchantReceipt;
            return this;
        }

        public MyPOSPreauthorizationCompletion.Builder printCustomerReceipt(int printCustomerReceipt) {
            this.printCustomerReceipt = printCustomerReceipt;
            return this;
        }

        public MyPOSPreauthorizationCompletion.Builder reference(String referenceNumber, int referenceType) {
            this.referenceNumber = referenceNumber;
            this.referenceType = referenceType;
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
            if(!ReferenceType.isInBound(referenceType)) {
                throw new IllegalArgumentException("reference type out of bound");
            }
            if(ReferenceType.isEnabled(referenceType) && (referenceNumber == null || referenceNumber.length() > 20 || referenceNumber.isEmpty() || !referenceNumber.matches("[\\p{Punct}\\p{Digit}\\p{Space}\\p{Latin}]+$"))) {
                throw new IllegalArgumentException("incorrect reference number");
            }

            return new MyPOSPreauthorizationCompletion(this);
        }
    }


}
