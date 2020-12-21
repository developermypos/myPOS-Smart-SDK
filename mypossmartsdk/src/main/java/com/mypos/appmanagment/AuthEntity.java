package com.mypos.appmanagment;

import android.os.Parcel;
import android.os.Parcelable;

public class AuthEntity implements Parcelable {

    protected int blkNo;
    protected M1KeyTypeEnum keyType;
    protected byte[] pwd;
    protected String uid;

    public AuthEntity() {
    }

    protected AuthEntity(Parcel in) {
        blkNo = in.readInt();
        keyType = M1KeyTypeEnum.valueOf(in.readString());
        pwd = in.createByteArray();
        uid = in.readString();
    }

    public static final Creator<AuthEntity> CREATOR = new Creator<AuthEntity>() {
        @Override
        public AuthEntity createFromParcel(Parcel in) {
            return new AuthEntity(in);
        }

        @Override
        public AuthEntity[] newArray(int size) {
            return new AuthEntity[size];
        }
    };

    public int getBlkNo() {
        return blkNo;
    }

    public M1KeyTypeEnum getKeyType() {
        return keyType;
    }

    public byte[] getPwd() {
        return pwd;
    }

    public String getUid() {
        return uid;
    }

    public void setBlkNo(int blkNo) {
        this.blkNo = blkNo;
    }

    public void setKeyType(M1KeyTypeEnum keyType) {
        this.keyType = keyType;
    }

    public void setPwd(byte[] pwd) {
        this.pwd = pwd;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(blkNo);
        dest.writeString(keyType.name());
        dest.writeByteArray(pwd);
        dest.writeString(uid);
    }

    public void readFromParcel(Parcel in) {
        blkNo = in.readInt();
        keyType = M1KeyTypeEnum.valueOf(in.readString());
        pwd = in.createByteArray();
        uid = in.readString();
    }

    public enum M1KeyTypeEnum {
        KEYTYPE_A,
        KEYTYPE_B;

        private M1KeyTypeEnum() {
        }
    }
}
