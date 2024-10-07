package com.mypos.smartsdk;

import java.io.Serializable;

public class MyPOSBase<D extends MyPOSBase> implements Serializable {

    private String      foreignTransactionId;
    private int         printMerchantReceipt;
    private int         printCustomerReceipt;
    private int         baseColor;

    public String getForeignTransactionId() {
        return foreignTransactionId;
    }

    public D setForeignTransactionId(String foreignTransactionId) {
        this.foreignTransactionId = foreignTransactionId;
        return (D) this;
    }

    public int getPrintMerchantReceipt() {
        return printMerchantReceipt;
    }

    public D setPrintMerchantReceipt(int printMerchantReceipt) {
        this.printMerchantReceipt = printMerchantReceipt;
        return (D) this;
    }

    public int getPrintCustomerReceipt() {
        return printCustomerReceipt;
    }

    public D setPrintCustomerReceipt(int printCustomerReceipt) {
        this.printCustomerReceipt = printCustomerReceipt;
        return (D) this;
    }

    public int getBaseColor() {
        return baseColor;
    }

    public D setBaseColor(int baseColor) {
        this.baseColor = baseColor;
        return (D) this;
    }

    protected MyPOSBase(BaseBuilder builder) {
        this.printMerchantReceipt = builder.printMerchantReceipt;
        this.printCustomerReceipt = builder.printCustomerReceipt;
        this.foreignTransactionId = builder.foreignTransactionId;
        this.baseColor = builder.baseColor;
    }

    public static BaseBuilder builder() {
        return new BaseBuilder();
    }

    public static class BaseBuilder<T extends BaseBuilder<T>> implements Serializable {

        private String foreignTransactionId;
        private int    printMerchantReceipt;
        private int    printCustomerReceipt;
        private int    baseColor;

        public T printMerchantReceipt(int printMerchantReceipt) {
            this.printMerchantReceipt = printMerchantReceipt;
            return (T) this;
        }

        public T printCustomerReceipt(int printCustomerReceipt) {
            this.printCustomerReceipt = printCustomerReceipt;
            return (T) this;
        }

        public T baseColor(int baseColor) {
            this.baseColor = baseColor;
            return (T) this;
        }

        public T foreignTransactionId(String foreignTransactionId) {
            this.foreignTransactionId = foreignTransactionId;
            return (T) this;
        }

        public MyPOSBase build() {
            return new MyPOSBase(this);
        }

    }
}