package com.mypos.smartsdk;


import com.mypos.smartsdk.exceptions.GiftCardUnsupportedParamsException;
import com.mypos.smartsdk.exceptions.InvalidAmountException;
import com.mypos.smartsdk.exceptions.InvalidEReceiptReceiverException;
import com.mypos.smartsdk.exceptions.MissingCurrencyException;

/**
 * Describes a refund
 */
public class MyPOSRefund extends MyPOSBase<MyPOSRefund> {

    private double              refundAmount;
    private boolean             motoTransaction;
    private boolean             giftCardTransaction;
    private Currency            currency;
    private String              motoPassword;
    private boolean             fixedPinpad;
    private String              eReceiptReceiver;

    private MyPOSRefund(Builder builder) {
        super(builder);
        this.refundAmount = builder.refundAmount;
        this.currency = builder.currency;
        this.motoTransaction = builder.motoTransaction;
        this.giftCardTransaction = builder.giftCardTransaction;
        this.motoPassword = builder.motoPassword;
        this.fixedPinpad = builder.fixedPinpad;
        this.eReceiptReceiver = builder.eReceiptReceiver;
    }


    public static Builder builder() {
        return new Builder();
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public MyPOSRefund setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public MyPOSRefund setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public boolean isGiftCardTransaction() {
        return giftCardTransaction;
    }

    public MyPOSRefund setGiftCardTransaction(boolean giftCardTransaction) {
        this.giftCardTransaction = giftCardTransaction;
        return this;
    }

    public boolean isMotoTransaction() {
        return motoTransaction;
    }

    public MyPOSRefund setMotoTransaction(boolean motoTransaction) {
        this.motoTransaction = motoTransaction;
        return this;
    }

    public String getMotoPassword() {
        return motoPassword;
    }

    public MyPOSRefund setMotoPassword(String motoPassword) {
        this.motoPassword = motoPassword;
        return this;
    }

    public boolean getFixedPinpad() {
        return fixedPinpad;
    }

    public MyPOSRefund setFixedPinpad(boolean fixedPinpad) {
        this.fixedPinpad = fixedPinpad;
        return this;
    }

    public String getEReceiptReceiver() {
        return eReceiptReceiver;
    }

    public MyPOSRefund setEReceiptReceiver(String eReceiptReceiver) {
        this.eReceiptReceiver = eReceiptReceiver;
        return this;
    }

    public static final class Builder extends MyPOSBase.BaseBuilder<Builder> {
        private boolean         motoTransaction;
        private boolean         giftCardTransaction;
        private Double          refundAmount;
        private Currency        currency;
        private String          motoPassword;
        private boolean         fixedPinpad = true;
        private String          eReceiptReceiver;

        public Builder refundAmount(Double productAmount) {
            this.refundAmount = productAmount;
            return this;
        }

        public Builder currency(Currency currency) {
            this.currency = currency;
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

        public Builder motoPassword(String motoPassword) {
            this.motoPassword = motoPassword;
            return this;
        }
        public Builder fixedPinpad(boolean fixedPinpad) {
            this.fixedPinpad = fixedPinpad;
            return this;
        }

        public Builder eReceiptReceiver(String eReceiptReceiver) {
            this.eReceiptReceiver = eReceiptReceiver;
            return this;
        }

        public MyPOSRefund build() throws InvalidAmountException, MissingCurrencyException, GiftCardUnsupportedParamsException {
            if (this.refundAmount == null || this.refundAmount <= 0.0D) {
                throw new InvalidAmountException("Invalid amount");
            }
            if (this.currency == null) {
                throw new MissingCurrencyException("Invalid currency");
            }

            if(motoTransaction && giftCardTransaction) {
                throw new GiftCardUnsupportedParamsException("GIFT CARD does not support MO/TO transactions");
            }

            if(eReceiptReceiver != null && !MyPOSUtil.isEmailValid(eReceiptReceiver) && !MyPOSUtil.isMobileNumberValid(eReceiptReceiver)) {
                throw new InvalidEReceiptReceiverException("e-receipt credential is not valid");
            }

            return new MyPOSRefund(this);
        }
    }
}
