package com.mypos.smartsdk;

import com.mypos.smartsdk.exceptions.InvalidAmountException;
import com.mypos.smartsdk.exceptions.MissingCurrencyException;
import com.mypos.smartsdk.exceptions.MissingRecipientException;

/**
 * Created by rumen.ivanov on 24.10.2017 г..
 */

public class MyPOSPaymentRequest {
    private Double productAmount;
    private Currency currency;
    private String GSM;
    private String eMail;
    private String reason;
    private String recipientName;
    private String requestCode;
    private Integer expiryDays;

    MyPOSPaymentRequest(MyPOSPaymentRequest.Builder builder) {
        this.productAmount = builder.productAmount;
        this.currency = builder.currency;
        this.GSM = builder.GSM;
        this.eMail = builder.eMail;
        this.reason = builder.reason;
        this.recipientName = builder.recipientName;
        this.requestCode = builder.requestCode;
        this.expiryDays = builder.expiryDays;
    }

    public static MyPOSPaymentRequest.Builder builder() {
        return new MyPOSPaymentRequest.Builder();
    }

    public double getProductAmount() {
        return this.productAmount.doubleValue();
    }

    public MyPOSPaymentRequest setProductAmount(double productAmount) {
        this.productAmount = Double.valueOf(productAmount);
        return this;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public MyPOSPaymentRequest setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public String getGSM() {
        return this.GSM;
    }

    public MyPOSPaymentRequest setGSM(String GSM) {
        this.GSM = GSM;
        return this;
    }

    public String getEMail() {
        return this.eMail;
    }

    public MyPOSPaymentRequest setEMail(String eMail) {
        this.eMail = eMail;
        return this;
    }

    public String getReason() {
        return this.reason;
    }

    public MyPOSPaymentRequest setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public String getRecipientName() {
        return this.recipientName;
    }

    public MyPOSPaymentRequest setRecipientName(String recipientName) {
        this.recipientName = recipientName;
        return this;
    }

    public String getRequestCode() {
        return this.requestCode;
    }

    public MyPOSPaymentRequest setRequestCode(String requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public Integer getExpiryDays() {
        return this.expiryDays;
    }

    public MyPOSPaymentRequest setExpiryDays(Integer expiryDays) {
        this.expiryDays = expiryDays;
        return this;
    }

    public static class Builder {
        private Double productAmount;
        private Currency currency;
        private String GSM;
        private String eMail;
        private String reason;
        private String recipientName;
        private String requestCode;
        private Integer expiryDays;

        public Builder() {
        }

        public MyPOSPaymentRequest.Builder productAmount(Double productAmount) {
            this.productAmount = productAmount;
            return this;
        }

        public MyPOSPaymentRequest.Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public MyPOSPaymentRequest.Builder GSM(String GSM) {
            this.GSM = GSM;
            return this;
        }

        public MyPOSPaymentRequest.Builder eMail(String eMail) {
            this.eMail = eMail;
            return this;
        }

        public MyPOSPaymentRequest.Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public MyPOSPaymentRequest.Builder recipientName(String recipientName) {
            this.recipientName = recipientName;
            return this;
        }

        public MyPOSPaymentRequest.Builder requestCode(String requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public MyPOSPaymentRequest.Builder expiryDays(int expiryDays) {
            this.expiryDays = Integer.valueOf(expiryDays);
            return this;
        }

        public MyPOSPaymentRequest build() throws MissingCurrencyException, InvalidAmountException, MissingRecipientException {
            if(this.productAmount != null && this.productAmount.doubleValue() > 0.0D) {
                if(this.currency == null) {
                    throw new MissingCurrencyException("Missing currency");
                } else if(this.GSM != null && !this.GSM.isEmpty() || this.eMail != null && !this.eMail.isEmpty()) {
                    return new MyPOSPaymentRequest(this);
                } else {
                    throw new MissingRecipientException("Missing recipient");
                }
            } else {
                throw new InvalidAmountException("Invalid or missing amount");
            }
        }
    }
}
