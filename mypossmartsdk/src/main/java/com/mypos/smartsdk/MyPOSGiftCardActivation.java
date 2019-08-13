package com.mypos.smartsdk;


import com.MyPOSBase;
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
public class MyPOSGiftCardActivation extends MyPOSBase<MyPOSGiftCardActivation> {

    private double      productAmount;
    private String      foreignTransactionId;
    private Currency    currency;


    MyPOSGiftCardActivation(Builder builder) {
        super(builder);
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

    public static class Builder extends MyPOSBase.BaseBuilder<Builder> {
        private Double      productAmount;
        private String      foreignTransactionId;
        private Currency    currency;

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
