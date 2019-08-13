package com.mypos.smartsdk;


import com.MyPOSBase;
import com.mypos.smartsdk.exceptions.GiftCardUnsupportedParamsException;
import com.mypos.smartsdk.exceptions.InvalidAmountException;
import com.mypos.smartsdk.exceptions.MissingCurrencyException;

/**
 * Describes a refund
 */
public class MyPOSRefund extends MyPOSBase {

    private double              refundAmount;
    private boolean             motoTransaction;
    private boolean             giftCardTransaction;
    private Currency            currency;
    private String              motoPassword;

    private MyPOSRefund(Builder builder) {
        super(builder);
        this.refundAmount = builder.refundAmount;
        this.currency = builder.currency;
        this.motoTransaction = builder.motoTransaction;
        this.giftCardTransaction = builder.giftCardTransaction;
        this.motoPassword = builder.motoPassword;
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

    public static final class Builder extends MyPOSBase.BaseBuilder {
        private boolean         motoTransaction;
        private boolean         giftCardTransaction;
        private Double          refundAmount;
        private Currency        currency;
        private String          motoPassword;

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

            return new MyPOSRefund(this);
        }
    }
}
