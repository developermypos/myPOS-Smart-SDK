package com.mypos.smartsdk;

import com.mypos.smartsdk.exceptions.InvalidReferenceNumberException;
import com.mypos.smartsdk.exceptions.InvalidReferenceTypeException;
import com.mypos.smartsdk.exceptions.MissingPreauthCodeException;

/**
 * Cancel a preauthorization
 */
public class MyPOSPreauthorizationCancellation extends MyPOSBase<MyPOSPreauthorizationCancellation> {

    private String              preauthorizationCode;
    private int                 printMerchantReceipt;
    private int                 printCustomerReceipt;


    MyPOSPreauthorizationCancellation(Builder builder) {
        super(builder);
        this.preauthorizationCode = builder.preauthorizationCode;
        this.printMerchantReceipt = builder.printMerchantReceipt;
        this.printCustomerReceipt = builder.printCustomerReceipt;
    }


    public static MyPOSPreauthorizationCancellation.Builder builder() {
        return new MyPOSPreauthorizationCancellation.Builder();
    }

    public String getPreauthorizationCode() {
        return preauthorizationCode;
    }

    public MyPOSPreauthorizationCancellation setPreauthorizationCode(String preauthorizationCode) {
        this.preauthorizationCode = preauthorizationCode;
        return this;
    }

    public int getPrintMerchantReceipt() {
        return printMerchantReceipt;
    }

    public MyPOSPreauthorizationCancellation setPrintMerchantReceipt(int printMerchantReceipt) {
        this.printMerchantReceipt = printMerchantReceipt;
        return this;
    }

    public int getPrintCustomerReceipt() {
        return printCustomerReceipt;
    }

    public MyPOSPreauthorizationCancellation setPrintCustomerReceipt(int printCustomerReceipt) {
        this.printCustomerReceipt = printCustomerReceipt;
        return this;
    }

    public static class Builder extends BaseBuilder<Builder> {
        private String              preauthorizationCode;
        private int                 printMerchantReceipt;
        private int                 printCustomerReceipt;

        public MyPOSPreauthorizationCancellation.Builder preauthorizationCode(String preauthorizationCode) {
            this.preauthorizationCode = preauthorizationCode;
            return this;
        }

        public MyPOSPreauthorizationCancellation.Builder printMerchantReceipt(int printMerchantReceipt) {
            this.printMerchantReceipt = printMerchantReceipt;
            return this;
        }

        public MyPOSPreauthorizationCancellation.Builder printCustomerReceipt(int printCustomerReceipt) {
            this.printCustomerReceipt = printCustomerReceipt;
            return this;
        }

        public MyPOSPreauthorizationCancellation build() throws MissingPreauthCodeException, InvalidReferenceTypeException, InvalidReferenceNumberException {
            if (this.preauthorizationCode == null || preauthorizationCode.isEmpty()) {
                throw new MissingPreauthCodeException("Missing preauthorization code");
            }

            return new MyPOSPreauthorizationCancellation(this);
        }
    }




}
