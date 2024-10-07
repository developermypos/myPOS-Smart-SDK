package com.mypos.smartsdk;


import com.mypos.smartsdk.exceptions.GiftCardUnsupportedParamsException;
import com.mypos.smartsdk.exceptions.InvalidAmountException;
import com.mypos.smartsdk.exceptions.InvalidEReceiptReceiverException;
import com.mypos.smartsdk.exceptions.InvalidOperatorCodeException;
import com.mypos.smartsdk.exceptions.InvalidReferenceNumberException;
import com.mypos.smartsdk.exceptions.InvalidReferenceTypeException;
import com.mypos.smartsdk.exceptions.InvalidTipAmountException;
import com.mypos.smartsdk.exceptions.MissingCurrencyException;

/**
 * Describes a payment
 */
public class MyPOSVendingPayment extends MyPOSBase<MyPOSVendingPayment> {

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
    private String      motoPAN;
    private String      motoExpDate;
    private boolean     fixedPinpad;
    private boolean     mastercardSonicBranding;
    private boolean     visaSensoryBranding;
    private boolean     dccEnabled;
    private boolean     showAmount;
    private boolean     showCancel;
    private int         cardDetectionTimeout;

    MyPOSVendingPayment(MyPOSVendingPayment.Builder builder) {
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
        this.motoPAN = builder.motoPAN;
        this.motoExpDate = builder.motoExpDate;
        this.fixedPinpad = builder.fixedPinpad;
        this.mastercardSonicBranding = builder.mastercardSonicBranding;
        this.visaSensoryBranding = builder.visaSensoryBranding;
        this.cardDetectionTimeout = builder.cardDetectionTimeout;
        this.dccEnabled = builder.dccEnabled;
        this.showAmount = builder.showAmount;
        this.showCancel = builder.showCancel;
    }


    public static MyPOSVendingPayment.Builder builder() {
        return new MyPOSVendingPayment.Builder();
    }

    public double getProductAmount() {
        return productAmount;
    }

    public MyPOSVendingPayment setProductAmount(double productAmount) {
        this.productAmount = productAmount;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public MyPOSVendingPayment setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public boolean isTippingModeEnabled() {
        return tippingModeEnabled;
    }

    public MyPOSVendingPayment setTippingModeEnabled(boolean tippingModeEnabled) {
        this.tippingModeEnabled = tippingModeEnabled;
        return this;
    }

    public double getTipAmount() {
        return tipAmount;
    }

    public MyPOSVendingPayment setTipAmount(double tipAmount) {
        this.tipAmount = tipAmount;
        return this;
    }


    public boolean isMotoTransaction() {
        return motoTransaction;
    }

    public MyPOSVendingPayment setMotoTransaction(boolean motoTransaction) {
        this.motoTransaction = motoTransaction;
        return this;
    }

    public boolean isGiftCardTransaction() {
        return giftCardTransaction;
    }

    public MyPOSVendingPayment setGiftCardTransaction(boolean giftCardTransaction) {
        this.giftCardTransaction = giftCardTransaction;
        return this;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public MyPOSVendingPayment setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
        return this;
    }

    public String getMotoPassword() {
        return motoPassword;
    }

    public MyPOSVendingPayment setMotoPassword(String motoPassword) {
        this.motoPassword = motoPassword;
        return this;
    }

    public String getMotoPAN() {
        return motoPAN;
    }

    public MyPOSVendingPayment setMotoPAN(String motoPAN) {
        this.motoPAN = motoPAN;
        return this;
    }

    public String getMotoExpDate() {
        return motoExpDate;
    }

    public MyPOSVendingPayment setMotoExpDate(String motoExpDate) {
        this.motoExpDate = motoExpDate;
        return this;
    }

    public boolean getFixedPinpad() {
        return fixedPinpad;
    }

    public MyPOSVendingPayment setFixedPinpad(boolean fixedPinpad) {
        this.fixedPinpad = fixedPinpad;
        return this;
    }

    public boolean mastercardSonicBranding() {
        return mastercardSonicBranding;
    }

    public MyPOSVendingPayment setMastercardSonicBranding(boolean  mastercardSonicBranding) {
        this. mastercardSonicBranding = mastercardSonicBranding;
        return this;
    }

    public boolean visaSensoryBranding() {
        return visaSensoryBranding;
    }

    public MyPOSVendingPayment setVisaSensoryBranding(boolean  visaSensoryBranding) {
        this. visaSensoryBranding = visaSensoryBranding;
        return this;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public int getReferenceType() {
        return referenceType;
    }

    public MyPOSVendingPayment setReference(String referenceNumber, int referenceType) {
        this.referenceNumber = referenceNumber;
        this.referenceType = referenceType;
        return this;
    }

    public MyPOSVendingPayment setCardDetectionTimeout(int cardDetectionTimeout) {
        this.cardDetectionTimeout = cardDetectionTimeout;
        return this;
    }

    public int getCardDetectionTimeout() {
        return cardDetectionTimeout;
    }

    public MyPOSVendingPayment setDccEnabled(boolean dccEnabled) {
        this.dccEnabled = dccEnabled;
        return this;
    }

    public boolean isDccEnabled() {
        return dccEnabled;
    }

    public MyPOSVendingPayment setShowAmount(boolean showAmount) {
        this.showAmount = showAmount;
        return this;
    }

    public boolean isShowAmount() {
        return showAmount;
    }

    public MyPOSVendingPayment setShowCancel(boolean showCancel) {
        this.showCancel = showCancel;
        return this;
    }

    public boolean isShowCancel() {
        return showCancel;
    }

    public static class Builder extends MyPOSBase.BaseBuilder<MyPOSVendingPayment.Builder> {
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
        private String      motoPAN;
        private String      motoExpDate;
        private boolean     fixedPinpad = true;
        private boolean     mastercardSonicBranding = true;
        private boolean     visaSensoryBranding = true;
        private boolean     dccEnabled = true;
        private boolean     showAmount = true;
        private boolean     showCancel = true;
        private int         cardDetectionTimeout;

        public MyPOSVendingPayment.Builder productAmount(Double productAmount) {
            this.productAmount = productAmount;
            return this;
        }

        public MyPOSVendingPayment.Builder tipAmount(Double tipAmount) {
            this.tipAmount = tipAmount;
            return this;
        }

        public MyPOSVendingPayment.Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public MyPOSVendingPayment.Builder tippingModeEnabled(boolean tippingModeEnabled) {
            this.tippingModeEnabled = tippingModeEnabled;
            return this;
        }

        public MyPOSVendingPayment.Builder giftCardTransaction(boolean giftCardTransaction) {
            this.giftCardTransaction = giftCardTransaction;
            return this;
        }

        public MyPOSVendingPayment.Builder motoTransaction(boolean motoTransaction) {
            this.motoTransaction = motoTransaction;
            return this;
        }

        public MyPOSVendingPayment.Builder operatorCode(String operatorCode) {
            this.operatorCode = operatorCode;
            return this;
        }

        public MyPOSVendingPayment.Builder motoPassword(String motoPassword) {
            this.motoPassword = motoPassword;
            return this;
        }

        public MyPOSVendingPayment.Builder motoPAN(String motoPAN) {
            this.motoPAN = motoPAN;
            return this;
        }

        public MyPOSVendingPayment.Builder motoExpDate(String motoExpDate) {
            this.motoExpDate = motoExpDate;
            return this;
        }

        public MyPOSVendingPayment.Builder mastercardSonicBranding(boolean mastercardSonicBranding) {
            this.mastercardSonicBranding = mastercardSonicBranding;
            return this;
        }

        public MyPOSVendingPayment.Builder visaSensoryBranding(boolean visaSensoryBranding) {
            this.visaSensoryBranding = visaSensoryBranding;
            return this;
        }

        public MyPOSVendingPayment.Builder showAmount(boolean showAmount) {
            this.showAmount = showAmount;
            return this;
        }

        public MyPOSVendingPayment.Builder showCancel(boolean showCancel) {
            this.showCancel = showCancel;
            return this;
        }

        public MyPOSVendingPayment.Builder fixedPinpad(boolean fixedPinpad) {
            this.fixedPinpad = fixedPinpad;
            return this;
        }

        public MyPOSVendingPayment.Builder reference(String referenceNumber, int referenceType) {
            this.referenceNumber = referenceNumber;
            this.referenceType = referenceType;
            return this;
        }

        public MyPOSVendingPayment.Builder cardDetectionTimeout(int cardDetectionTimeout) {
            this.cardDetectionTimeout = cardDetectionTimeout;
            return this;
        }

        public MyPOSVendingPayment.Builder dccEnabled(boolean dccEnabled) {
            this.dccEnabled = dccEnabled;
            return this;
        }

        public MyPOSVendingPayment build() throws InvalidAmountException, InvalidTipAmountException, MissingCurrencyException, GiftCardUnsupportedParamsException, InvalidOperatorCodeException, InvalidReferenceTypeException, InvalidReferenceNumberException {
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
                    throw new InvalidOperatorCodeException("incorrect operator code");
                }
            }

            if(!ReferenceType.isInBound(referenceType)) {
                throw new InvalidReferenceTypeException("reference type out of bound");
            }
            if(ReferenceType.isEnabled(referenceType) && !MyPOSUtil.isReferenceNumberValid(referenceNumber)) {
                throw new InvalidReferenceNumberException("incorrect reference number");
            }

            return new MyPOSVendingPayment(this);
        }
    }
}
