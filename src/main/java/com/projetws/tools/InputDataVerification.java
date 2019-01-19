package com.projetws.tools;

import org.apache.commons.text.StringEscapeUtils;

public class InputDataVerification
{

    /**
     * @param text input
     * @param minLength
     * @param maxLength
     * @return true or false
     */
    public static boolean textLengthOk(String text, int minLength, int maxLength)
    {
        return !(text.length() > maxLength || text.length() < minLength);
    }

    /**
     * @param text
     * @return text escaped
     */
    public static String escape(String text)
    {
        return StringEscapeUtils.escapeHtml4(text);
    }

    /**
     * @param email
     * @return boolean
     */
    public static boolean emailAddressOk(String email)
    {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
