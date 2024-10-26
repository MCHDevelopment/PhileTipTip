package com.mch.philetiptip.Logic;

public class Helper {

    public static Integer parseIntOrNull(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Boolean isNullOrEmpty(String checkString) {
        return checkString == null || checkString.isEmpty();
    }

}
