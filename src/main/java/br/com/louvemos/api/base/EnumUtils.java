package br.com.louvemos.api.base;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andersson
 */
public class EnumUtils {

    public static <E extends Enum<E>> boolean isEnumValid(String str, Class<E> enumType) {
        E[] listEnum = enumType.getEnumConstants();
        for (Enum<E> enumClass : listEnum) {
            if (enumClass.name().equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

}
