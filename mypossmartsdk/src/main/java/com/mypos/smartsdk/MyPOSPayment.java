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
public class MyPOSPayment extends MyPOSBase<MyPOSPayment> {

    private boolean     tippingModeEnabled;
    private boolean     motoTransaction;
    private boolean     giftCardTransaction;
    private double      productAmount;
    private double      tipAmount;
    private Currency    currency;
    private int         printMerchantReceipt;
    private int         printCustomerReceipt;
    private String      operatorCode;
    private String      referenceNumber;
    private int         referenceType;
    private String      motoPassword;
    private boolean     fixedPinpad;
    private boolean     mastercardSonicBranding;
    private boolean     visaSensoryBranding;
    private String      eReceiptReceiver;


    MyPOSPayment(Builder builder) {
        super(builder);
        this.productAmount = builder.productAmount;
        this.currency = builder.currency;
        this.tippingModeEnabled = builder.tippingModeEnabled;
        this.tipAmount = builder.tipAmount;
        this.motoTransaction = builder.motoTransaction;
        this.giftCardTransaction = builder.giftCardTransaction;
        this.printMerchantReceipt = builder.printMerchantReceipt;
        this.printCustomerReceipt = builder.printCustomerReceipt;
        this.operatorCode = builder.operatorCode;
        this.referenceNumber = builder.referenceNumber;
        this.referenceType = builder.referenceType;
        this.motoPassword = builder.motoPassword;
        this.fixedPinpad = builder.fixedPinpad;
        this.mastercardSonicBranding = builder.mastercardSonicBranding;
        this.visaSensoryBranding = builder.visaSensoryBranding;
        this.eReceiptReceiver = builder.eReceiptReceiver;
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

    public int getPrintMerchantReceipt() {
        return printMerchantReceipt;
    }

    public MyPOSPayment setPrintMerchantReceipt(int printMerchantReceipt) {
        this.printMerchantReceipt = printMerchantReceipt;
        return this;
    }

    public int getPrintCustomerReceipt() {
        return printCustomerReceipt;
    }

    public MyPOSPayment setPrintCustomerReceipt(int printCustomerReceipt) {
        this.printCustomerReceipt = printCustomerReceipt;
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

    public boolean getFixedPinpad() {
        return fixedPinpad;
    }

    public MyPOSPayment setFixedPinpad(boolean fixedPinpad) {
        this.fixedPinpad = fixedPinpad;
        return this;
    }
        
    public boolean mastercardSonicBranding() {
        return mastercardSonicBranding;
    }

    public MyPOSPayment setMastercardSonicBranding(boolean  mastercardSonicBranding) {
        this. mastercardSonicBranding = mastercardSonicBranding;
        return this;
    }

    public boolean visaSensoryBranding() {
        return visaSensoryBranding;
    }

    public MyPOSPayment setVisaSensoryBranding(boolean  visaSensoryBranding) {
        this. visaSensoryBranding = visaSensoryBranding;
        return this;
    }

    public String getEReceiptReceiver() {
        return eReceiptReceiver;
    }

    public MyPOSPayment setEReceiptReceiver(String eReceiptReceiver) {
        this.eReceiptReceiver = eReceiptReceiver;
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

    public static class Builder extends BaseBuilder<Builder>{
        private boolean     tippingModeEnabled;
        private boolean     motoTransaction;
        private boolean     giftCardTransaction;
        private double      tipAmount;
        private Double      productAmount;
        private Currency    currency;
        private int         printMerchantReceipt;
        private int         printCustomerReceipt;
        private String      operatorCode;
        private String      referenceNumber;
        private int         referenceType;
        private String      motoPassword;
        private boolean     fixedPinpad = true;
        private boolean     mastercardSonicBranding = true;
        private boolean     visaSensoryBranding = true;
        private String      eReceiptReceiver;

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

        public Builder printMerchantReceipt(int printMerchantReceipt) {
            this.printMerchantReceipt = printMerchantReceipt;
            return this;
        }

        public Builder printCustomerReceipt(int printCustomerReceipt) {
            this.printCustomerReceipt = printCustomerReceipt;
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
        public Builder mastercardSonicBranding(boolean mastercardSonicBranding) {
            this.mastercardSonicBranding = mastercardSonicBranding;
            return this;
        }

        public Builder visaSensoryBranding(boolean visaSensoryBranding) {
            this.visaSensoryBranding = visaSensoryBranding;
            return this;
        }

        public Builder fixedPinpad(boolean fixedPinpad) {
            this.fixedPinpad = fixedPinpad;
            return this;
        }

        public Builder reference(String referenceNumber, int referenceType) {
            this.referenceNumber = referenceNumber;
            this.referenceType = referenceType;
            return this;
        }

        public Builder eReceiptReceiver(String eReceiptReceiver) {
            this.eReceiptReceiver = eReceiptReceiver;
            return this;
        }

        public MyPOSPayment build() throws InvalidAmountException, InvalidTipAmountException, MissingCurrencyException, GiftCardUnsupportedParamsException, InvalidOperatorCodeException, InvalidReferenceTypeException, InvalidReferenceNumberException {
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

            if(eReceiptReceiver != null && !MyPOSUtil.isEmailValid(eReceiptReceiver) && !MyPOSUtil.isMobileNumberValid(eReceiptReceiver)) {
                throw new InvalidEReceiptReceiverException("e-receipt receiver is not valid");
            }

            return new MyPOSPayment(this);
        }
    }
}
