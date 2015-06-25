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

    public static int getValidTime(String timeString) {
        String upperTime = timeString.trim().toUpperCase();
        if (isValidTime(timeString)) {
            int blank, h, m;
            blank = upperTime.lastIndexOf(" ");
            h = upperTime.lastIndexOf("H");
            m = upperTime.lastIndexOf("M");

            int sv = 0;

            if (h > 0 && m < 0) {
                sv = 1;
            }
            if (h < 0 && m > 0) {
                sv = 2;
            }
            if (h > 0 && m > 0 && blank < 0 && m > h) {
                sv = 3;
            }
            if (h > 0 && m > 0 && blank < 0 && m < h) {
                sv = 4;
            }
            if (h > 0 && m > 0 && blank > 0 && m > h) {
                sv = 5;
            }
            if (h > 0 && m > 0 && blank > 0 && m < h) {
                sv = 6;
            }

            String hours = "0", minuts = "0";

            switch (sv) {
                case 1:
                    //Solo horas
                    hours = timeString.substring(0, h);
                    break;
                case 2:
                    minuts = timeString.substring(0, m);
                    //Solo minutos
                    break;
                case 3:
                    //horas y minutos sin blanco hm
                    hours = timeString.substring(0, h);
                    minuts = timeString.substring(h, m);
                    break;
                case 4:
                    //horas y minutos sin blanco mh

                    minuts = timeString.substring(0, m);
                    hours = timeString.substring(m, h);
                    break;
                case 5:
                    //horas y minutos con blanco hm

                    hours = timeString.substring(0, h);
                    minuts = timeString.substring(blank, m);
                    break;
                case 6:
                    //horas y minutos con blanco mh

                    hours = timeString.substring(blank, h);
                    minuts = timeString.substring(0, m);
                    break;
            }

            return (Integer.parseInt(minuts.trim()) * 60) + (Integer.parseInt(hours.trim()) * 60 * 60);
        } else {
            return -1;
        }
    }

    public static String getValidTime(int secndsTime) {
        String timestring = "0m";
        int hours = 0;
        int minuts = secndsTime / 60;
        if (minuts >= 60) {
            hours = minuts / 60;
            minuts -= (60 * hours);
        }
        if (hours > 0) {
            timestring = hours + "h " + minuts + "m";
        } else {
            timestring = minuts + "m";
        }

        return timestring;
    }

    public static boolean isValidTime(String timeString) {
        String upperTime = timeString.trim().toUpperCase();

        int blank, h, m, H, M, sv = 0;
        blank = upperTime.lastIndexOf(" ");
        h = upperTime.lastIndexOf("H");
        m = upperTime.lastIndexOf("M");
        H = upperTime.indexOf("H");
        M = upperTime.indexOf("M");
        if (H < h) {
            return false;
        }
        if (M < m) {
            return false;
        }

        //h>0 y m<0
        //h<0 y m>0
        //h>0 y m>0 y b>0 y h<m
        //h>0 y m>0 y b>0 y h>m

        if (h > 0 && m < 0) {
            sv = 1;
        }
        if (h < 0 && m > 0) {
            sv = 2;
        }
        if (h > 0 && m > 0 && blank < 0 && m > h) {
            sv = 3;
        }
        if (h > 0 && m > 0 && blank < 0 && m < h) {
            sv = 4;
        }
        if (h > 0 && m > 0 && blank > 0 && m > h) {
            sv = 5;
        }
        if (h > 0 && m > 0 && blank > 0 && m < h) {
            sv = 6;
        }
        String hours = "0", minuts = "0";
        if (sv > 0) {
            switch (sv) {
                case 1:
                    //Solo horas
                    hours = timeString.substring(0, h);
                    break;
                case 2:
                    minuts = timeString.substring(0, m);
                    //Solo minutos
                    break;
                case 3:
                    //horas y minutos sin blanco hm
                    hours = timeString.substring(0, h);
                    minuts = timeString.substring(h, m);
                    break;
                case 4:
                    //horas y minutos sin blanco mh

                    minuts = timeString.substring(0, m);
                    hours = timeString.substring(m, h);
                    break;
                case 5:
                    //horas y minutos con blanco hm

                    hours = timeString.substring(0, h);
                    minuts = timeString.substring(blank, m);
                    break;
                case 6:
                    //horas y minutos con blanco mh

                    hours = timeString.substring(blank, h);
                    minuts = timeString.substring(0, m);
                    break;
            }

            try {
                int time = (Integer.parseInt(minuts.trim()) * 60) + (Integer.parseInt(hours.trim()) * 60 * 60);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        } else return false;
    }
}
