package com.mypos.smartsdk;

import com.mypos.smartsdk.exceptions.InvalidAmountException;
import com.mypos.smartsdk.exceptions.InvalidEReceiptReceiverException;
import com.mypos.smartsdk.exceptions.InvalidReferenceNumberException;
import com.mypos.smartsdk.exceptions.InvalidReferenceTypeException;
import com.mypos.smartsdk.exceptions.MissingCurrencyException;

/**
 * Creates a preauthorization transaction
 */

public class MyPOSPreauthorization extends MyPOSBase<MyPOSPreauthorization> {

    private boolean     motoTransaction;
    private double      productAmount;
    private Currency    currency;
    private int         printMerchantReceipt;
    private int         printCustomerReceipt;
    private String      referenceNumber;
    private int         referenceType;
    private String      motoPassword;
    private boolean     fixedPinpad;
    private String      eReceiptReceiver;

    MyPOSPreauthorization(Builder builder) {
        super(builder);
        this.productAmount = builder.productAmount;
        this.currency = builder.currency;
        this.motoTransaction = builder.motoTransaction;
        this.printMerchantReceipt = builder.printMerchantReceipt;
        this.printCustomerReceipt = builder.printCustomerReceipt;
        this.referenceNumber = builder.referenceNumber;
        this.referenceType = builder.referenceType;
        this.motoPassword = builder.motoPassword;
        this.fixedPinpad = builder.fixedPinpad;
        this.eReceiptReceiver = builder.eReceiptReceiver;
    }


    public static MyPOSPreauthorization.Builder builder() {
        return new MyPOSPreauthorization.Builder();
    }

    public double getProductAmount() {
        return productAmount;
    }

    public MyPOSPreauthorization setProductAmount(double productAmount) {
        this.productAmount = productAmount;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public MyPOSPreauthorization setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public boolean isMotoTransaction() {
        return motoTransaction;
    }

    public MyPOSPreauthorization setMotoTransaction(boolean motoTransaction) {
        this.motoTransaction = motoTransaction;
        return this;
    }

    public String getMotoPassword() {
        return motoPassword;
    }

    public MyPOSPreauthorization setMotoPassword(String motoPassword) {
        this.motoPassword = motoPassword;
        return this;
    }


    public boolean getFixedPinpad() {
        return fixedPinpad;
    }

    public MyPOSPreauthorization setFixedPinpad(boolean fixedPinpad) {
        this.fixedPinpad = fixedPinpad;
        return this;
    }

    public String getEReceiptReceiver() {
        return eReceiptReceiver;
    }

    public MyPOSPreauthorization setEReceiptReceiver(String eReceiptReceiver) {
        this.eReceiptReceiver = eReceiptReceiver;
        return this;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public int getReferenceType() {
        return referenceType;
    }

    public MyPOSPreauthorization setReference(String referenceNumber, int referenceType) {
        this.referenceNumber = referenceNumber;
        this.referenceType = referenceType;
        return this;
    }

    public static class Builder extends BaseBuilder<Builder> {
        private boolean     motoTransaction;
        private Double      productAmount;
        private Currency    currency;
        private int         printMerchantReceipt;
        private int         printCustomerReceipt;
        private String      referenceNumber;
        private int         referenceType;
        private String      motoPassword;
        private boolean     fixedPinpad = true;
        private String      eReceiptReceiver;

        public MyPOSPreauthorization.Builder productAmount(Double productAmount) {
            this.productAmount = productAmount;
            return this;
        }

        public MyPOSPreauthorization.Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public MyPOSPreauthorization.Builder motoTransaction(boolean motoTransaction) {
            this.motoTransaction = motoTransaction;
            return this;
        }

        public MyPOSPreauthorization.Builder printMerchantReceipt(int printMerchantReceipt) {
            this.printMerchantReceipt = printMerchantReceipt;
            return this;
        }

        public MyPOSPreauthorization.Builder printCustomerReceipt(int printCustomerReceipt) {
            this.printCustomerReceipt = printCustomerReceipt;
            return this;
        }

        public MyPOSPreauthorization.Builder motoPassword(String motoPassword) {
            this.motoPassword = motoPassword;
            return this;
        }

        public MyPOSPreauthorization.Builder fixedPinpad(boolean fixedPinpad) {
            this.fixedPinpad = fixedPinpad;
            return this;
        }

        public MyPOSPreauthorization.Builder reference(String referenceNumber, int referenceType) {
            this.referenceNumber = referenceNumber;
            this.referenceType = referenceType;
            return this;
        }

        public MyPOSPreauthorization.Builder eReceiptReceiver(String eReceiptCredential) {
            this.eReceiptReceiver = eReceiptCredential;
            return this;
        }

        public MyPOSPreauthorization build() throws InvalidAmountException, MissingCurrencyException, InvalidReferenceTypeException, InvalidReferenceNumberException {
            if (this.productAmount == null || this.productAmount <= 0.0D) {
                throw new InvalidAmountException("Invalid or missing amount");
            }
            if (this.currency == null) {
                throw new MissingCurrencyException("Missing currency");
            }
            if(!ReferenceType.isInBound(referenceType)) {
                throw new InvalidReferenceTypeException("reference type out of bound");
            }
            if(ReferenceType.isEnabled(referenceType) && !MyPOSUtil.isReferenceNumberValid(referenceNumber)) {
                throw new InvalidReferenceNumberException("incorrect reference number");
            }
            if(eReceiptReceiver != null && !MyPOSUtil.isEmailValid(eReceiptReceiver) && !MyPOSUtil.isMobileNumberValid(eReceiptReceiver)) {
                throw new InvalidEReceiptReceiverException("e-receipt credential is not valid");
            }

            return new MyPOSPreauthorization(this);
        }
    }


}
