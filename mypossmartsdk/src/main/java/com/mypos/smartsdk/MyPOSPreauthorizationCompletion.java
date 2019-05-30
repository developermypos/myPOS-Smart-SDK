package com.mypos.smartsdk;

import com.mypos.smartsdk.exceptions.InvalidAmountException;
import com.mypos.smartsdk.exceptions.InvalidReferenceNumberException;
import com.mypos.smartsdk.exceptions.InvalidReferenceTypeException;
import com.mypos.smartsdk.exceptions.MissingCurrencyException;
import com.mypos.smartsdk.exceptions.MissingPreauthCodeException;

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

    MyPOSPreauthorizationCompletion(MyPOSPreauthorizationCompletion.Builder builder) {
        this.productAmount = builder.productAmount;
        this.foreignTransactionId = builder.foreignTransactionId;
        this.currency = builder.currency;
        this.preauthorizationCode = builder.preauthorizationCode;
        this.printMerchantReceipt = builder.printMerchantReceipt;
        this.printCustomerReceipt = builder.printCustomerReceipt;
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

    public static class Builder {
        private Double              productAmount;
        private String              foreignTransactionId;
        private Currency            currency;
        private String              preauthorizationCode;
        private int                 printMerchantReceipt;
        private int                 printCustomerReceipt;

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

        public MyPOSPreauthorizationCompletion build() throws InvalidAmountException, MissingCurrencyException, MissingPreauthCodeException, InvalidReferenceTypeException, InvalidReferenceNumberException {
            if (this.productAmount == null || this.productAmount <= 0.0D) {
                throw new InvalidAmountException("Invalid or missing amount");
            }
            if (this.currency == null) {
                throw new MissingCurrencyException("Missing currency");
            }
            if (this.preauthorizationCode == null || preauthorizationCode.isEmpty()) {
                throw new MissingPreauthCodeException("Missing preauthorization code");
            }

            return new MyPOSPreauthorizationCompletion(this);
        }
    }


}
