package com.mozido.channels.nextweb.i18n;

import com.google.gwt.i18n.client.Constants;

/**
 * Common constants for application
 *
 * @author Alexander Manusovich
 */
public interface CmConstants extends Constants {
    String cPleaseWait();

    // SelectNode...
    String cSelectNode();

    // User is not active
    String cUserIsNotActive();

    // Balances
    String cBalances();

    String cConfiguration();

    String cLimits();

    // Amount should not exceed node balance
    String vAmountShouldNotExceed();

    // This field is mandatory
    String vFieldIsMandatory();

    // Number is not valid
    String vNumberIsNotValid();

    // Amount is not valid
    String vAmountIsNotValid();

    // PIN has to be 4 digits
    String vPinInvalid();

    // Value is not the same
    String vIsNotTheSameValue();

    // Ops! That is error...
    String cErrorTitle();
}
