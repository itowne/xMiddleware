package com.open.item.config.xss;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static String DEFAULT_VALUE = "";

    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        int paramLength = values.length;
        if (values != null && paramLength > 0) {
            String[] encodedValues = new String[paramLength];
            for (int i = 0; i < paramLength; i++) {
                encodedValues[i] = cleanXSS(values[i]);
            }
            return encodedValues;
        }
        return null;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (StringUtils.isBlank(value)) {
            return DEFAULT_VALUE;
        }
        return cleanXSS(value);
    }

    @Override
    public String getHeader(String header) {
        String value = super.getHeader(header);
        if (StringUtils.isBlank(value)) {
            return DEFAULT_VALUE;
        }
        return cleanXSS(value);
    }

    private String cleanXSS(String value) {
        value = HtmlUtils.htmlEscape(value);
        value = JavaScriptUtils.javaScriptEscape(value);
        return value;
    }

    // private String cleanXSS(String value) {
    // // You'll need to remove the spaces from the html entities below
    // value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    // value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
    // value = value.replaceAll("'", "&#39;");
    // value = value.replaceAll("eval\\((.*)\\)", "");
    // value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']",
    // "\"\"");
    // value = value.replaceAll("<script", "");
    // value = value.replaceAll("delete ", "").replaceAll("update ",
    // "").replaceAll("insert ", "");
    // return value;
    // }
}
