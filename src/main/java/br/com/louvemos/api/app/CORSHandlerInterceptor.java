package br.com.louvemos.api.app;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

public class CORSHandlerInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object handler) throws Exception {
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");

        if (HttpMethod.OPTIONS.matches(httpRequest.getMethod())) {
            httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
            httpResponse.setHeader("Access-Control-Max-Age", "86400");
            httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, api_key, Authorization, x-requested-with");
//            response.setHeader("Vary", "Origin");
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return false;
        }

        return true;
    }

}
