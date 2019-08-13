package com.mypos.smartsdk;

import com.MyPOSBase;
import com.mypos.smartsdk.exceptions.InvalidAmountException;
import com.mypos.smartsdk.exceptions.InvalidReferenceNumberException;
import com.mypos.smartsdk.exceptions.InvalidReferenceTypeException;
import com.mypos.smartsdk.exceptions.MissingCurrencyException;
import com.mypos.smartsdk.exceptions.MissingPreauthCodeException;

/**
 * Complete a preauthorization
 */
public class MyPOSPreauthorizationCompletion extends MyPOSBase<MyPOSPreauthorizationCompletion> {

    private double              productAmount;
    private String              preauthorizationCode;
    private Currency            currency;

    MyPOSPreauthorizationCompletion(Builder builder) {
        super(builder);
        this.productAmount = builder.productAmount;
        this.currency = builder.currency;
        this.preauthorizationCode = builder.preauthorizationCode;
    }


    public static Builder builder() {
        return new Builder();
    }

    public double getProductAmount() {
        return productAmount;
    }

    public MyPOSPreauthorizationCompletion setProductAmount(double productAmount) {
        this.productAmount = productAmount;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public MyPOSPreauthorizationCompletion setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public String getPreauthorizationCode() {
        return preauthorizationCode;
    }

    public MyPOSPreauthorizationCompletion setPreauthorizationCode(String preauthorizationCode) {
        this.preauthorizationCode = preauthorizationCode;
        return this;
    }

    public static class Builder extends MyPOSBase.BaseBuilder<Builder> {
        private Double              productAmount;
        private Currency            currency;
        private String              preauthorizationCode;

        public Builder productAmount(Double productAmount) {
            this.productAmount = productAmount;
            return this;
        }

        public Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public Builder preauthorizationCode(String preauthorizationCode) {
            this.preauthorizationCode = preauthorizationCode;
            return this;
        }

        public MyPOSPreauthorizationCompletion build() throws InvalidAmountException, MissingCurrencyException, MissingPreauthCodeException, InvalidReferenceTypeException, InvalidReferenceNumberException {
            if (this.productAmount == null || this.productAmount <= 0.0D) {
                throw new InvalidAmountException("Invalid or missing amount");
            }
            if (this.currency == null) {
                throw new MissingCurrencyException("Missing currency");
            }
            if (this.preauthorizationCode == null || preauthorizationCode.isEmpty()) {
                throw new MissingPreauthCodeException("Missing preauthorization code");
            }

            return new MyPOSPreauthorizationCompletion(this);
        }
    }


}
