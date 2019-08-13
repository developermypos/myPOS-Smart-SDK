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
public class MyPOSPayment extends MyPOSBase {

    private boolean     tippingModeEnabled;
    private boolean     motoTransaction;
    private boolean     giftCardTransaction;
    private double      productAmount;
    private double      tipAmount;
    private Currency    currency;
    private String      operatorCode;
    private String      referenceNumber;
    private int         referenceType;
    private String      motoPassword;


    MyPOSPayment(Builder builder) {
        super(builder);
        this.productAmount = builder.productAmount;
        this.currency = builder.currency;
        this.tippingModeEnabled = builder.tippingModeEnabled;
        this.tipAmount = builder.tipAmount;
        this.motoTransaction = builder.motoTransaction;
        this.giftCardTransaction = builder.giftCardTransaction;
        this.operatorCode = builder.operatorCode;
        this.referenceNumber = builder.referenceNumber;
        this.referenceType = builder.referenceType;
        this.motoPassword = builder.motoPassword;
    }


    public static Builder builder() {
        return new Builder();
    }

    public double getProductAmount() {
        return productAmount;
    }

    public MyPOSPayment setProductAmount(double productAmount) {
        this.productAmount = productAmount;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public MyPOSPayment setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public boolean isTippingModeEnabled() {
        return tippingModeEnabled;
    }

    public MyPOSPayment setTippingModeEnabled(boolean tippingModeEnabled) {
        this.tippingModeEnabled = tippingModeEnabled;
        return this;
    }

    public double getTipAmount() {
        return tipAmount;
    }

    public MyPOSPayment setTipAmount(double tipAmount) {
        this.tipAmount = tipAmount;
        return this;
    }

    public boolean isMotoTransaction() {
        return motoTransaction;
    }

    public MyPOSPayment setMotoTransaction(boolean motoTransaction) {
        this.motoTransaction = motoTransaction;
        return this;
    }

    public boolean isGiftCardTransaction() {
        return giftCardTransaction;
    }

    public MyPOSPayment setGiftCardTransaction(boolean giftCardTransaction) {
        this.giftCardTransaction = giftCardTransaction;
        return this;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public MyPOSPayment setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
        return this;
    }

    public String getMotoPassword() {
        return motoPassword;
    }

    public MyPOSPayment setMotoPassword(String motoPassword) {
        this.motoPassword = motoPassword;
        return this;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public int getReferenceType() {
        return referenceType;
    }

    public MyPOSPayment setReference(String referenceNumber, int referenceType) {
        this.referenceNumber = referenceNumber;
        this.referenceType = referenceType;
        return this;
    }

    public static class Builder extends MyPOSBase.BaseBuilder {
        private boolean     tippingModeEnabled;
        private boolean     motoTransaction;
        private boolean     giftCardTransaction;
        private double      tipAmount;
        private Double      productAmount;
        private Currency    currency;
        private String      operatorCode;
        private String      referenceNumber;
        private int         referenceType;
        private String      motoPassword;

        public Builder productAmount(Double productAmount) {
            this.productAmount = productAmount;
            return this;
        }

        public Builder tipAmount(Double tipAmount) {
            this.tipAmount = tipAmount;
            return this;
        }

        public Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public Builder tippingModeEnabled(boolean tippingModeEnabled) {
            this.tippingModeEnabled = tippingModeEnabled;
            return this;
        }

        public Builder giftCardTransaction(boolean giftCardTransaction) {
            this.giftCardTransaction = giftCardTransaction;
            return this;
        }

        public Builder motoTransaction(boolean motoTransaction) {
            this.motoTransaction = motoTransaction;
            return this;
        }

        public Builder operatorCode(String operatorCode) {
            this.operatorCode = operatorCode;
            return this;
        }

        public Builder motoPassword(String motoPassword) {
            this.motoPassword = motoPassword;
            return this;
        }

        public Builder reference(String referenceNumber, int referenceType) {
            this.referenceNumber = referenceNumber;
            this.referenceType = referenceType;
            return this;
        }

        public MyPOSPayment build() throws InvalidAmountException, InvalidTipAmountException, MissingCurrencyException, GiftCardUnsupportedParamsException, InvalidOperatorCodeExcepton, InvalidReferenceTypeException, InvalidReferenceNumberException {
            if (this.productAmount == null || this.productAmount <= 0.0D) {
                throw new InvalidAmountException("Invalid or missing amount");
            }
            if (this.currency == null) {
                throw new MissingCurrencyException("Missing currency");
            }

            if (this.tippingModeEnabled && this.tipAmount <= 0.0D) {
                throw new InvalidTipAmountException("Invalid tip amount");
            }

            if(motoTransaction && giftCardTransaction) {
                throw new GiftCardUnsupportedParamsException("GIFT CARD does not support MO/TO transactions");
            }

            if (operatorCode != null ) {

                boolean valid = true;

                if (operatorCode.length() > 4 || operatorCode.isEmpty()) {
                    valid = false;
                }
                else {
                    try {
                        if(Integer.parseInt(operatorCode) < 0) {
                            valid = false;
                        }
                    } catch (NumberFormatException e) {
                        valid = false;
                    }
                }

                if(!valid) {
                    throw new InvalidOperatorCodeExcepton("incorrect operator code");
                }
            }

            if(!ReferenceType.isInBound(referenceType)) {
                throw new InvalidReferenceTypeException("reference type out of bound");
            }
            if(ReferenceType.isEnabled(referenceType) && !MyPOSUtil.isReferenceNumberValid(referenceNumber)) {
                throw new InvalidReferenceNumberException("incorrect reference number");
            }

            return new MyPOSPayment(this);
        }
    }
}
