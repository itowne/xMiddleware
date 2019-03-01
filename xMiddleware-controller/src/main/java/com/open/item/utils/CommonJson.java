package com.open.item.utils;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 通用json返回
 * 
 * @author towne
 * @date Oct 11, 2018
 */
public class CommonJson implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3251370070381008135L;

    private static final String RES_CODE = "resCode";
    private static final String RES_MSG = "resMsg";
    public static final String SUCC = "1";
    public static final String ERROR = "0";

    public static String dataResponse(String code, Object obj) {
        JSONObject json = new JSONObject();
        json.put(RES_CODE, code);
        json.put(RES_MSG, obj);
        return json.toJSONString();
    }
}
