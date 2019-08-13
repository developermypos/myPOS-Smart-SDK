package com.mypos.smartsdk;

import com.MyPOSBase;
import com.mypos.smartsdk.exceptions.InvalidReferenceNumberException;
import com.mypos.smartsdk.exceptions.InvalidReferenceTypeException;
import com.mypos.smartsdk.exceptions.MissingPreauthCodeException;

/**
 * Cancel a preauthorization
 */
public class MyPOSPreauthorizationCancellation extends MyPOSBase {

    private String              preauthorizationCode;


    MyPOSPreauthorizationCancellation(Builder builder) {
        super(builder);
        this.preauthorizationCode = builder.preauthorizationCode;
    }


    public static Builder builder() {
        return new Builder();
    }

    public String getPreauthorizationCode() {
        return preauthorizationCode;
    }

    public MyPOSPreauthorizationCancellation setPreauthorizationCode(String preauthorizationCode) {
        this.preauthorizationCode = preauthorizationCode;
        return this;
    }
    public static class Builder extends MyPOSBase.BaseBuilder<Builder> {
        private String              preauthorizationCode;

        public Builder preauthorizationCode(String preauthorizationCode) {
            this.preauthorizationCode = preauthorizationCode;
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
