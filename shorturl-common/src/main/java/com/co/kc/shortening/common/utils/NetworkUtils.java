package com.co.kc.shortening.common.utils;

import com.google.common.collect.Maps;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author kc
 */
public class NetworkUtils {
    private NetworkUtils() {
    }

    public static Map<String, String> getHeader(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();

        Map<String, String> result = new HashMap<>(16);
        for (; headerNames.hasMoreElements(); ) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headerValue = request.getHeaders(headerName);
            StringJoiner headerValueJoiner = new StringJoiner(",");
            for (; headerValue.hasMoreElements(); ) {
                headerValueJoiner.add(headerValue.nextElement());
            }
            result.put(headerName, headerValueJoiner.toString());
        }
        return result;
    }

    public static Map<String, String> getHeader(HttpServletResponse response) {
        Collection<String> headerNames = response.getHeaderNames();

        Map<String, String> result = Maps.newHashMapWithExpectedSize(headerNames.size());
        for (String headerName : headerNames) {
            Collection<String> headerValue = response.getHeaders(headerName);
            result.put(headerName, String.join(",", headerValue));
        }
        return result;
    }

    public static Map<String, Object> getBody(HttpServletRequest request) {
        Enumeration<String> enumeration = request.getParameterNames();

        Map<String, Object> parameterMap = new HashMap<>(16);
        while (enumeration.hasMoreElements()) {
            String parameter = enumeration.nextElement();
            String[] value = request.getParameterValues(parameter);
            parameterMap.put(parameter, value);
        }

        return parameterMap;
    }
}
