package com.mypos.smartsdk;

import com.mypos.smartsdk.MyPOSPayment;

public class MyPOSBase<D extends MyPOSBase> {

    private boolean     isFiscalDevice;
    private int         resultScreenOnTimeOut;
    private String      foreignTransactionId;

    public boolean isFiscalDevice() {
        return isFiscalDevice;
    }

    public D setFiscalDevice(boolean fiscalDevice) {
        isFiscalDevice = fiscalDevice;
        return (D) this;
    }

    public int getResultScreenOnTimeOut() {
        return resultScreenOnTimeOut;
    }

    public D setResultScreenOnTimeOut(int resultScreenOnTimeOut) {
        this.resultScreenOnTimeOut = resultScreenOnTimeOut;
        return (D) this;
    }

    public String getForeignTransactionId() {
        return foreignTransactionId;
    }

    public D setForeignTransactionId(String foreignTransactionId) {
        this.foreignTransactionId = foreignTransactionId;
        return (D) this;
    }

    protected MyPOSBase(BaseBuilder builder) {
        this.isFiscalDevice = builder.isFiscalDevice;
        this.resultScreenOnTimeOut = builder.resultScreenOnTimeOut;
        this.foreignTransactionId = builder.foreignTransactionId;
    }

    public static BaseBuilder builder() {
        return new BaseBuilder();
    }

    public static class BaseBuilder<T extends BaseBuilder<T>> {
        private boolean isFiscalDevice;
        private int resultScreenOnTimeOut;
        private String foreignTransactionId;

        public T isFiscalDevice(boolean isFiscalDevice) {
            this.isFiscalDevice = isFiscalDevice;
            return (T) this;
        }

        public T resultScreenOnTimeOut(int resultScreenOnTimeOut) {
            if (resultScreenOnTimeOut < 0) {
                resultScreenOnTimeOut = 0;
            }
            else if (resultScreenOnTimeOut < 3 && resultScreenOnTimeOut > 0) {
                resultScreenOnTimeOut = 3;
            }

            this.resultScreenOnTimeOut = resultScreenOnTimeOut;
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
