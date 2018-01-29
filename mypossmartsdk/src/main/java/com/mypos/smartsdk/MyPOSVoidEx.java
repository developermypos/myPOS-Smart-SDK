package com.mypos.smartsdk;


/**
 * Describes a refund
 */
public class MyPOSVoidEx {

    private int                 STAN;
    private String              authCode;
    private String              dateTime;

    private MyPOSVoidEx(Builder builder) {
        this.STAN = builder.STAN;
        this.authCode = builder.authCode;
        this.dateTime = builder.dateTime;
    }


    public static Builder builder() {
        return new Builder();
    }

    public int getSTAN() {
        return STAN;
    }

    public MyPOSVoidEx setSTAN(int STAN) {
        this.STAN = STAN;
        return this;
    }

    public String getAuthCode() {
        return authCode;
    }

    public MyPOSVoidEx setAuthCode(String authCode) {
        this.authCode = authCode;
        return this;
    }

    public String getDateTime() {
        return dateTime;
    }

    public MyPOSVoidEx setDateTime(String dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public static final class Builder {
        private int                 STAN;
        private String              authCode;
        private String              dateTime;

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

        public MyPOSVoidEx build() {
            if (STAN == 0) {
                throw new IllegalArgumentException("Invalid STAN");
            }
            if (authCode == null) {
                throw new IllegalArgumentException("missing or invalid auth code");
            }
            if (dateTime == null) {
                throw new IllegalArgumentException("missing or invalid date time");
            }

            return new MyPOSVoidEx(this);
        }
    }
}
