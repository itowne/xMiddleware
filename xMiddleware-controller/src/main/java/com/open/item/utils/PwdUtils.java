package com.open.item.utils;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户密码加密工具
 * 
 * @author towne
 * @date Sep 26, 2018
 */
public class PwdUtils {
    private static Logger logger = LoggerFactory.getLogger(PwdUtils.class);

    public static String encryptPwd(String pwd) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] b = md.digest(pwd.getBytes());
            return Base64.encodeBase64String(b);
        } catch (Exception e) {
            logger.info("pwd加密失败!原因:{}", e.getMessage());
            return null;
        }
    }

    public static boolean comparePwd(String encryptPwd, String oriPwd) {
        return StringUtils.equals(encryptPwd, encryptPwd(oriPwd));
    }
}
