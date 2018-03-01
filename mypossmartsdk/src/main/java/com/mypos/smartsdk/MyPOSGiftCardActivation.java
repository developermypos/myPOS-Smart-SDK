package com.mypos.smartsdk;


import com.mypos.smartsdk.exceptions.GiftCardUnsupportedParamsException;
import com.mypos.smartsdk.exceptions.InvalidAmountException;
import com.mypos.smartsdk.exceptions.InvalidOperatorCodeExcepton;
import com.mypos.smartsdk.exceptions.InvalidReferenceNumberException;
import com.mypos.smartsdk.exceptions.InvalidReferenceTypeException;
import com.mypos.smartsdk.exceptions.InvalidTipAmountException;
import com.mypos.smartsdk.exceptions.MissingCurrencyException;

/**
 * Describes a payment
 */
public class MyPOSGiftCardActivation {

    private double      productAmount;
    private String      foreignTransactionId;
    private Currency    currency;
    private int         printMerchantReceipt;
    private int         printCustomerReceipt;


    MyPOSGiftCardActivation(Builder builder) {
        this.productAmount = builder.productAmount;
        this.foreignTransactionId = builder.foreignTransactionId;
        this.currency = builder.currency;
        this.printMerchantReceipt = builder.printMerchantReceipt;
        this.printCustomerReceipt = builder.printCustomerReceipt;
    }


    public static Builder builder() {
        return new Builder();
    }

    public double getProductAmount() {
        return productAmount;
    }

    public MyPOSGiftCardActivation setProductAmount(double productAmount) {
        this.productAmount = productAmount;
        return this;
    }

    public String getForeignTransactionId() {
        return foreignTransactionId;
    }

    public MyPOSGiftCardActivation setForeignTransactionId(String foreignTransactionId) {
        this.foreignTransactionId = foreignTransactionId;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public MyPOSGiftCardActivation setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public int getPrintMerchantReceipt() {
        return printMerchantReceipt;
    }

    public MyPOSGiftCardActivation setPrintMerchantReceipt(int printMerchantReceipt) {
        this.printMerchantReceipt = printMerchantReceipt;
        return this;
    }

    public int getPrintCustomerReceipt() {
        return printCustomerReceipt;
    }

    public MyPOSGiftCardActivation setPrintCustomerReceipt(int printCustomerReceipt) {
        this.printCustomerReceipt = printCustomerReceipt;
        return this;
    }

    public static class Builder {
        private Double      productAmount;
        private String      foreignTransactionId;
        private Currency    currency;
        private int         printMerchantReceipt;
        private int         printCustomerReceipt;

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

        public Builder printMerchantReceipt(int printMerchantReceipt) {
            this.printMerchantReceipt = printMerchantReceipt;
            return this;
        }

        public Builder printCustomerReceipt(int printCustomerReceipt) {
            this.printCustomerReceipt = printCustomerReceipt;
            return this;
        }

        public MyPOSGiftCardActivation build() throws InvalidAmountException, InvalidTipAmountException, MissingCurrencyException, InvalidOperatorCodeExcepton, InvalidReferenceTypeException, InvalidReferenceNumberException {
            if (this.productAmount == null || this.productAmount <= 0.0D) {
                throw new InvalidAmountException("Invalid or missing amount");
            }
            if (this.currency == null) {
                throw new MissingCurrencyException("Missing currency");
            }

            return new MyPOSGiftCardActivation(this);
        }
    }
}
