package br.com.louvemos.api.base;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author heits
 */
public class ControllerUtils {

    public static LinkedHashMap<String, SortDirectionEnum> parseSortParam(String sortParam) {
        if (StringUtils.isBlank(sortParam)) {
            return null;
        }

        sortParam = sortParam.toLowerCase();

        LinkedHashMap<String, SortDirectionEnum> keyToDir = new LinkedHashMap();
        String[] sortElements = sortParam.split(",");
        for (String sortElement : sortElements) {
            String[] keyVal = sortElement.split(":");
            if (keyVal.length != 2) {
                return null;
            }
            String key = keyVal[0];
            String dir = keyVal[1];
            if (StringUtils.isBlank(key) || !EnumUtils.isEnumValid(dir, SortDirectionEnum.class)) {
                return null;
            }
            Object oldVal = keyToDir.put(key, SortDirectionEnum.valueOf(dir));

            // Check if there was already a value at that key. Fail if true.
            if (oldVal != null) {
                return null;
            }
        }

        return keyToDir;
    }

    public static boolean isLatLngParamsValid(Double lat, Double lng) {
        if (lat == null || lng == null) {
            return false;
        }
        if (lat > 180.0 || lat < -180.0 || lng > 180.0 || lng < -180.0) {
            return false;
        }
        return true;
    }

    public static int adjustFirstResult(Integer firstResult) {
        int adjusted = 0;
        if (firstResult == null || firstResult < 0) {
            adjusted = 0;
        } else {
            adjusted = firstResult;
        }
        return adjusted;
    }

    public static int adjustMaxResults(Integer maxResults, int maxResultsDefault, int maxResultsUpperBound) {
        int adjusted;
        if (maxResults == null || maxResults <= 0) {
            adjusted = maxResultsDefault;
        } else if (maxResults > maxResultsUpperBound) {
            adjusted = maxResultsUpperBound;
        } else {
            adjusted = maxResults;
        }
        return adjusted;
    }

    public static int adjustIntegerParameter(Integer param) {
        int adjusted = 0;
        if (param == null || param < 0) {
            adjusted = 0;
        } else {
            adjusted = param;
        }
        return adjusted;
    }

    public static long adjustLongParameter(Long param) {
        long adjusted = 0;
        if (param == null || param < 0) {
            adjusted = 0;
        } else {
            adjusted = param;
        }
        return adjusted;
    }

    public static List<Long> parseCSVToLongList(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        String[] strList = str.split(",");

        List<Long> longList = new ArrayList();
        for (String strElem : strList) {
            if (!StringUtils.containsOnlyNumbers(strElem)) {
                return null;
            }
            longList.add(Long.parseLong(strElem));
        }
        return longList;
    }

    public static List<String> parseCSVToStringList(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        return Arrays.stream(str.split(",")).collect(Collectors.toList());
    }

    public static <E extends Enum<E>> List<E> parseCSVToEnumList(String str, Class<E> enumType) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        String[] strList = str.split(",");

        List<E> enumList = new ArrayList();
        for (String strElem : strList) {
            if (!EnumUtils.isEnumValid(strElem, enumType)) {
                return null;
            }
            enumList.add(E.valueOf(enumType, strElem));
        }
        return enumList;
    }
}
