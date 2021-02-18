package com.mypos.smartsdk.data;

import android.database.Cursor;
import android.os.Bundle;

import java.io.Serializable;

public class POSInfo implements Serializable {

    private String TID;
    private String currencyName;
    private String currencyCode;
    private MerchantData merchantData;

    public POSInfo() {
        TID = "";
        currencyName = "";
        currencyCode = "";
        merchantData = new MerchantData();
    }

    public String getTID() {
        return TID;
    }

    public void setTID(String TID) {
        this.TID = TID;
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

    public MerchantData getMerchantData() {
        return merchantData;
    }

    public void setMerchantData(MerchantData merchantData) {
        this.merchantData = merchantData;
    }

    public Bundle asBundle() {
        Bundle ret = new Bundle();

        ret.putString("TID", TID);
        ret.putString("CurrencyName", currencyName);
        ret.putString("CurrencyCode", currencyCode);
        ret.putBundle("MerchantData", merchantData.asBundle());

        return ret;
    }

    public void parseFromBundle(Bundle ret) {
        TID = ret.getString("TID");
        currencyName = ret.getString("CurrencyName");
        currencyCode = ret.getString("CurrencyCode");
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
                ", currencyName='" + currencyName + "'\n" +
                ", currencyCode='" + currencyCode + "'\n" +
                ", merchantData='" + merchantData.toString() + "'\n" +
                '}';
    }
}
