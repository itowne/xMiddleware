/**
 *
 */
package com.open.item.utils;

import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 序号生成工具
 *
 * @author towne
 * @version 1.0.0
 * @date Mar 4, 2016 3:05:22 PM
 */
public class IdWorkerUtils {

    private static final String REGEX_LENGTH = "^([A-Za-z0-9])*$";

    /**
     * 生成随机序列号
     *
     * @return 返回22位随机字符串
     */
    public static synchronized String uuidWorker() {
        UUID uuid = UUID.randomUUID();
        byte[] byUuid = new byte[16];
        long2Bytes(uuid.getMostSignificantBits(), byUuid, 0);
        long2Bytes(uuid.getLeastSignificantBits(), byUuid, 8);
        String randomStr = Base58.encode(byUuid);
        // 如果生成的随机数小于22位,随机补一位
        if (StringUtils.length(randomStr) < 22) {
            return randomStr + RandomUtils.nextInt(0, 10);
        }
        return randomStr;
    }

    public static String artIdWorker() {
        return "ART" + uuidWorker();
    }

    public static String actIdWorker() {
        return "ACT" + uuidWorker();
    }

    public static String imgIdWorker() {
        return "IMG" + uuidWorker();
    }

    public static String votIdWorker() {
        return "VOT" + uuidWorker();
    }

    public static String virIdWorker() {
        return "VIR" + uuidWorker();
    }

    public static String usrIdWorker() {
        return "USR" + uuidWorker();
    }

    public static String vcrIdWorker() {
        return "VCR" + uuidWorker();
    }

    public static String picIdWorker() {
        return "PIC" + uuidWorker();
    }

    /**
     * 判断ID有效性： 1、长度24位 2、英文、数字组合
     *
     * @param id
     * @return
     */
    public static boolean isValidSerial(String id) {
        if (StringUtils.isBlank(id)) {
            return false;
        }
        if (StringUtils.length(id) != 24) {
            return false;
        }
        if (Pattern.matches(REGEX_LENGTH, id) == false) {
            return false;
        }
        return true;
    }

    protected static void long2Bytes(long value, byte[] bytes, int offset) {
        for (int i = 7; i > -1; i--) {
            bytes[offset++] = (byte) ((value >> 8 * i) & 0xFF);
        }
    }
}
