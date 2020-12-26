package br.com.louvemos.api.base;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author heits
 */
public class ServiceUtils {

    public interface IApiToDbKey {

        public String op(String apiKey, SortDirectionEnum apiValue);
    }

    public static LinkedHashMap<String, SortDirectionEnum> convertSortMapToDbKeys(
            LinkedHashMap<String, SortDirectionEnum> sortMap,
            String defaultKey,
            SortDirectionEnum defaultDir,
            IApiToDbKey apiToDbKeyFunc) {

        LinkedHashMap<String, SortDirectionEnum> sortWithDbKeys = new LinkedHashMap();
        if (sortMap != null) {
            for (Map.Entry<String, SortDirectionEnum> entry : sortMap.entrySet()) {
                String dbKey = apiToDbKeyFunc.op(entry.getKey(), entry.getValue());
                if (!StringUtils.isBlank(dbKey)) {
                    sortWithDbKeys.put(dbKey, entry.getValue());
                }
            }
        }
        // This adds a default ordering for results, avoiding two requests with the same parameters yielding different results.
        if (!sortWithDbKeys.containsKey(defaultKey)) {
            sortWithDbKeys.put(defaultKey, defaultDir);
        }
        return sortWithDbKeys;
    }

}
