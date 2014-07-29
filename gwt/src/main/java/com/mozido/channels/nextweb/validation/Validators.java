package com.mozido.channels.nextweb.validation;

import com.mozido.channels.nextweb.validation.validators.AmountValidator;
import com.mozido.channels.nextweb.validation.validators.MobileNumberValidator;
import com.mozido.channels.nextweb.validation.validators.NotNullValidator;
import com.mozido.channels.nextweb.validation.validators.PinValidator;
import com.mozido.channels.nextweb.validation.validators.SameValueValidator;

public class Validators {
    private static NotNullValidator notNullValidator;
    private static MobileNumberValidator mobileNumberValidator;
    private static PinValidator pinValidator;
    private static AmountValidator amountValidator;

    private Validators() {
    }

    public static NotNullValidator getNotNullValidator() {
        if (notNullValidator == null) {
            notNullValidator = new NotNullValidator();
        }
        return notNullValidator;
    }

    public static PinValidator getPinValidator() {
        if (pinValidator == null) {
            pinValidator = new PinValidator();
        }
        return pinValidator;
    }

    public static MobileNumberValidator getMobileNumberValidator() {
        if (mobileNumberValidator == null) {
            mobileNumberValidator = new MobileNumberValidator();
        }
        return mobileNumberValidator;
    }

    public static AmountValidator getAmountValidator() {
        if (amountValidator == null) {
            amountValidator = new AmountValidator();
        }
        return amountValidator;
    }

    public static SameValueValidator getSameValueValidator(
            final HasValue anotherComponent) {
        return new SameValueValidator(anotherComponent);
    }
}
