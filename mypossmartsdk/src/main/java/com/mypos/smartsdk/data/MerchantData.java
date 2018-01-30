package com.mypos.smartsdk.data;


import android.os.Bundle;

import java.io.Serializable;
import java.util.Locale;

public class MerchantData implements Serializable {

    public static final int    RECEIPT_FOOTER_NONE    = 0;
    public static final int    RECEIPT_FOOTER_CUSTOM  = 1;
    // Constants used as keys in the config table
    public static final String BILLING_DESCRIPTOR_KEY = "BillingDescriptor";
    public static final String ADDRESS_LINE_1_KEY     = "AddressLine1";
    public static final String ADDRESS_LINE_2_KEY     = "AddressLine2";

    private String merchantID;

    private String merchantName;

    private String addressLine1;

    private String addressLine2;

    private String billingDescriptor;

    /**
     *
     */
    private int receiptFooterType;

    private String customReceiptRow1;

    private String customReceiptRow2;

    public MerchantData() {
        merchantID = "";
        merchantName = "";
        addressLine1 = "";
        addressLine2 = "";
        billingDescriptor = "";
        receiptFooterType = RECEIPT_FOOTER_NONE;
        customReceiptRow1 = "";
        customReceiptRow2 = "";
    }

    public Bundle asBundle() {
        Bundle ret = new Bundle();

        ret.putString("MID", merchantID);
        ret.putString("merchant_name", merchantName);
        ret.putString("address_line1", addressLine1);
        ret.putString("address_line2", addressLine2);
        ret.putString("billing_descriptor", billingDescriptor);
        ret.putInt("receipt_footer_type", receiptFooterType);
        ret.putString("custom_receipt_row1", customReceiptRow1);
        ret.putString("custom_receipt_row2", customReceiptRow2);

        return ret;
    }

    public void parseFromBundle(Bundle ret) {
        merchantID = ret.getString("MID");
        merchantName = ret.getString("merchant_name");
        addressLine1 = ret.getString("address_line1");
        addressLine2 = ret.getString("address_line2");
        billingDescriptor = ret.getString("billing_descriptor");
        receiptFooterType = ret.getInt("receipt_footer_type");
        customReceiptRow1 = ret.getString("custom_receipt_row1");
        customReceiptRow2 = ret.getString("custom_receipt_row2");
    }


    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getBillingDescriptor() {
        return billingDescriptor;
    }

    public void setBillingDescriptor(String billingDescriptor) {
        this.billingDescriptor = billingDescriptor;
    }

    public int getReceiptFooterType() {
        return receiptFooterType;
    }

    public void setReceiptFooterType(int receiptFooterType) {
        this.receiptFooterType = receiptFooterType;
    }

    public String getCustomReceiptRow1() {
        return customReceiptRow1;
    }

    public void setCustomReceiptRow1(String customReceiptRow1) {
        this.customReceiptRow1 = customReceiptRow1;
    }

    public String getCustomReceiptRow2() {
        return customReceiptRow2;
    }

    public void setCustomReceiptRow2(String customReceiptRow2) {
        this.customReceiptRow2 = customReceiptRow2;
    }

    @Override
    public String toString() {
        return "MerchantData{" +
                "merchantID=" + merchantID +
                ", merchantName='" + merchantName + "'\n" +
                ", addressLine1='" + addressLine1 + "'\n" +
                ", addressLine2='" + addressLine2 + "'\n" +
                ", billingDescriptor='" + billingDescriptor + "'\n" +
                ", receiptFooterType=" + receiptFooterType + '\n' +
                ", customReceiptRow1='" + customReceiptRow1 + "'\n" +
                ", customReceiptRow2='" + customReceiptRow2 + "'\n" +
                '}';
    }
}
