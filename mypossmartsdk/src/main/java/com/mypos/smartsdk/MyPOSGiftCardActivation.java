package com.mypos.smartsdk;


import com.mypos.smartsdk.exceptions.InvalidAmountException;
import com.mypos.smartsdk.exceptions.InvalidOperatorCodeException;
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
    private boolean     fixedPinpad;


    MyPOSGiftCardActivation(Builder builder) {
        super(builder);
        this.productAmount = builder.productAmount;
        this.foreignTransactionId = builder.foreignTransactionId;
        this.currency = builder.currency;
        this.fixedPinpad = builder.fixedPinpad;
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

    public boolean getFixedPinpad() {
        return fixedPinpad;
    }

    public MyPOSGiftCardActivation setFixedPinpad(boolean fixedPinpad) {
        this.fixedPinpad = fixedPinpad;
        return this;
    }

    public static class Builder extends MyPOSBase.BaseBuilder<Builder> {
        private Double      productAmount;
        private String      foreignTransactionId;
        private Currency    currency;
        private boolean     fixedPinpad;

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

        public Builder fixedPinpad(boolean fixedPinpad) {
            this.fixedPinpad = fixedPinpad;
            return this;
        }

        public MyPOSGiftCardActivation build() throws InvalidAmountException, InvalidTipAmountException, MissingCurrencyException, InvalidOperatorCodeException, InvalidReferenceTypeException, InvalidReferenceNumberException {
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
