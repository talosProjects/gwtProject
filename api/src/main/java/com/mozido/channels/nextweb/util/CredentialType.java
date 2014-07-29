package com.mozido.channels.nextweb.util;

/**
 * @author igor.timoshko
 */
public enum CredentialType {
    EMAIL,
    PHONE, 
    user;

    public static String stringByLogin(final String login){
        if (login.contains("@")) {
            return "" + EMAIL;
        }
        // se cambio a USER en vez de PHONE que no tenia credenciales - Jul 14-2014
        return "" + user;
    }
}
