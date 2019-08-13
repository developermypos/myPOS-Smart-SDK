package com.mypos.smartsdk;


import com.MyPOSBase;
import com.mypos.smartsdk.exceptions.MissingAuthCodeException;
import com.mypos.smartsdk.exceptions.MissingDateTimeException;
import com.mypos.smartsdk.exceptions.MissingSTANException;

/**
 * Describes a refund
 */
public class MyPOSVoid extends MyPOSBase {

    private int                 STAN;
    private String              authCode;
    private String              dateTime;
    private boolean             voidLastTransactionFlag;

    private MyPOSVoid(Builder builder) {
        super(builder);
        this.STAN = builder.STAN;
        this.authCode = builder.authCode;
        this.dateTime = builder.dateTime;
        this.voidLastTransactionFlag = builder.voidLastTransactionFlag;
    }


    public static Builder builder() {
        return new Builder();
    }

    public int getSTAN() {
        return STAN;
    }

    public MyPOSVoid setSTAN(int STAN) {
        this.STAN = STAN;
        return this;
    }

    public String getAuthCode() {
        return authCode;
    }

    public MyPOSVoid setAuthCode(String authCode) {
        this.authCode = authCode;
        return this;
    }

    public String getDateTime() {
        return dateTime;
    }

    public MyPOSVoid setDateTime(String dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public boolean getVoidLastTransactionFlag() {
        return voidLastTransactionFlag;
    }

    public MyPOSVoid setVoidLastTransactionFlag(boolean voidLastTransactionFlag) {
        this.voidLastTransactionFlag = voidLastTransactionFlag;
        return this;
    }

    public static final class Builder extends MyPOSBase.BaseBuilder {
        private int                 STAN;
        private String              authCode;
        private String              dateTime;
        private boolean             voidLastTransactionFlag;

        public Builder STAN(int STAN) {
            this.STAN = STAN;
            return this;
        }

        public Builder authCode(String authCode) {
            this.authCode = authCode;
            return this;
        }

        public Builder dateTime(String dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder voidLastTransactionFlag(boolean voidLastTransactionFlag) {
            this.voidLastTransactionFlag = voidLastTransactionFlag;
            return this;
        }

        public MyPOSVoid build() throws MissingSTANException, MissingAuthCodeException, MissingDateTimeException{
            if(!voidLastTransactionFlag) {
                if (STAN == 0) {
                    throw new MissingSTANException("Invalid STAN");
                }
                if (authCode == null) {
                    throw new MissingAuthCodeException("missing or invalid auth code");
                }
                if (dateTime == null) {
                    throw new MissingDateTimeException("missing or invalid date time");
                }
            }
            return new MyPOSVoid(this);
        }
    }
}
