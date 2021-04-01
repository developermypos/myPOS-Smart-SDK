package com.mypos.smartsdk.data;

import android.database.Cursor;
import android.os.Bundle;

import java.io.Serializable;

public class POSInfo implements Serializable {

    public static final int INITIAL = 0;
    public static final int NOT_ACTIVATED = 1;
    public static final int MISSING_KEY_INDEX = 2;
    public static final int ACTIVATED = 3;
    public static final int MISSING_CONFIG_FILES = 4;

    private String TID;
    private String SN;
    private String currencyName;
    private String currencyCode;
    private String regNumber;
    private int deviceState;
    private MerchantData merchantData;

    public POSInfo() {
        TID = "";
        SN = "";
        currencyName = "";
        currencyCode = "";
        deviceState = INITIAL;
        merchantData = new MerchantData();
    }

    public String getTID() {
        return TID;
    }

    public void setTID(String TID) {
        this.TID = TID;
    }

    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public MerchantData getMerchantData() {
        return merchantData;
    }

    public void setMerchantData(MerchantData merchantData) {
        this.merchantData = merchantData;
    }

    public int getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(int deviceState) {
        this.deviceState = deviceState;
    }

    public Bundle asBundle() {
        Bundle ret = new Bundle();

        ret.putString("TID", TID);
        ret.putString("SN", SN);
        ret.putString("CurrencyName", currencyName);
        ret.putString("CurrencyCode", currencyCode);
        ret.putString("RegNumber", regNumber);
        ret.putInt("device_state", deviceState);
        ret.putBundle("MerchantData", merchantData.asBundle());

        return ret;
    }

    public void parseFromBundle(Bundle ret) {
        TID = ret.getString("TID");
        SN = ret.getString("SN");
        currencyName = ret.getString("CurrencyName");
        currencyCode = ret.getString("CurrencyCode");
        regNumber = ret.getString("RegNumber");
        deviceState = ret.getInt("device_state");
        merchantData.parseFromBundle(ret.getBundle("MerchantData"));
    }

    public void parseFromCursor(Cursor cursor) {
        if (cursor.getColumnIndex("tid") > -1)
          TID = cursor.getString(cursor.getColumnIndex("tid"));

        if (cursor.getColumnIndex("CurrencyName") > -1)
            currencyName = cursor.getString(cursor.getColumnIndex("CurrencyName"));

        if (cursor.getColumnIndex("CurrencyCode") > -1)
            currencyCode = cursor.getString(cursor.getColumnIndex("CurrencyCode"));

        if (cursor.getExtras() != null)
            merchantData.parseFromBundle(cursor.getExtras());
    }

    @Override
    public String toString() {
        return "POSInfo{" +
                "TID=" + TID +
                "SN=" + SN +
                ", currencyName='" + currencyName + "'\n" +
                ", currencyCode='" + currencyCode + "'\n" +
                ", regNumber='"    + regNumber    + "'\n" +
                ", device_state='" + deviceState + "'\n" +
                ", merchantData='" + merchantData.toString() + "'\n" +
                '}';
    }
}
