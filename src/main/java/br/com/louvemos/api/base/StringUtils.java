package br.com.louvemos.api.base;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtils {

    /**
     * Removes all non-number characters of the string
     *
     * @param str
     * @return
     */
    public static String onlyNumbers(String str) {
        return StringUtils.isBlank(str) ? str : str.replaceAll("[^\\d]", "");
    }

    /**
     * Verify if string has at least one number
     *
     * @param str
     * @return
     */
    public static boolean containsNumber(String str) {
        return str.matches(".*?[0-9].*?");

    }

    /**
     * Verify if string has only numbers
     *
     * @param str
     * @return
     */
    public static boolean containsOnlyNumbers(String str) {
        return str.matches("[0-9]+");
    }

    /**
     * <p>
     * Checks if a String is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     * @since 2.0
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Masks the string by alternating the the masking char. Ex: testing becomes
     * t*s*i*g
     *
     * @param targetString
     * @return
     */
    public static String maskString(String targetString) {
        for (int i = 1; i < targetString.length(); i++) {
            if (i % 2 != 0) {
                targetString = targetString.substring(0, i) + '*' + targetString.substring(i + 1);
            }
        }

        return targetString;
    }

    /**
     * Capitalize the string. Ex: "testing is good" becomes "Testing Is Good"
     *
     * @param targetString
     * @return
     */
    public static String capitalizeAll(String targetString) {
        if (StringUtils.isBlank(targetString)) {
            return targetString;
        }

        return Arrays.asList(targetString.split(" ")).stream().
                map(str -> {
                    return str.length() == 0 ? "" : Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
                })
                .collect(Collectors.joining(" "));
    }

    /**
     * Checks if char is a letter of alphabet.
     *
     * @param c
     * @return boolean
     */
    public static boolean IsBasicLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

}
