package com.troncodroide.pesoppo.util;

/**
 * Created by Tronco on 23/05/2015.
 */
public class ValidateUtil {

    public static boolean validateDate(String date) {
        String dateRegexp = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
        if (validateText(date)) {
            return date.trim().matches(dateRegexp);
        } else {
            return false;
        }
    }

    public static boolean validateText(String text) {
        return (text.trim().length() > 0);
    }
}
