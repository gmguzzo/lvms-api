package br.com.louvemos.api.base;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;

/**
 *
 * @author heits
 */
@Component
public class SerializationUtils {

    private static ObjectMapper om;

    @Autowired(required = false)
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    @PostConstruct
    public void init() {
        // Try to find Jackson object mapper on spring's message converter through spring context
        om = null;
        if (mappingJackson2HttpMessageConverter != null) {
            om = mappingJackson2HttpMessageConverter.getObjectMapper();
        }

        if (om == null) {
            // Default, local object mapper
            om = new ObjectMapper()
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
    }

    public static <T> T fromJson(String jsonStr, Class<T> clazz) {
        if (StringUtils.isBlank(jsonStr) || clazz == null) {
            return null;
        }

        T obj = null;
        try {
            obj = om.readValue(jsonStr, clazz);
        } catch (IOException ex) {
            Logger.getLogger(SerializationUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj;
    }

    public static String toJson(Object o) {
        if (o == null) {
            return null;
        }

        String jsonStr = null;
        try {
            jsonStr = om.writeValueAsString(o);
        } catch (IOException ex) {
            Logger.getLogger(SerializationUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonStr;
    }

    public static String toUrlEncoded(String str) {
        if (str == null) {
            return null;
        }

        return UriUtils.encode(str, Charset.defaultCharset());
    }

    public static String toFormUrlEncoded(Map<String, String> uriVars) {
        if (uriVars == null || uriVars.isEmpty()) {
            return null;
        }

        String formStr = Joiner
                .on("&")
                .withKeyValueSeparator("=")
                .join(UriUtils.encodeUriVariables(uriVars));

        return formStr;
    }

}
