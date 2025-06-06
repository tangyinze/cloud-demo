package com.tyz.rabbitmq.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @program: cloud-demo
 * @description: IDCardGenerator
 * @author: tyz
 * @create: 2025-06-06
 */
public class IDCardGenerator {
    // 示例地区代码，可以根据需要扩展
    private static final String[] AREA_CODES = {"110000", "120000", "310000", "500000"};
    private static final Random random = new Random();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    // 加权因子
    private static final int[] WEIGHTS = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    // 校验码映射
    private static final char[] CHECK_CODES = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    public static String generateIdCardNo() {
        // 随机选择一个地区代码
        String areaCode = AREA_CODES[random.nextInt(AREA_CODES.length)];
        // 随机生成出生日期
        LocalDate birthDate = LocalDate.of(1970 + random.nextInt(50), random.nextInt(12) + 1, random.nextInt(28) + 1);
        String birthDateStr = birthDate.format(dateFormatter);
        // 随机生成顺序码（前17位）和性别（偶数女，奇数男）  从001到999
        int orderCode = random.nextInt(999) + 1;
        // 奇数代表男性，偶数代表女性（这里我们随机选择性别）
        int genderCode = random.nextInt(2) * 2 + 1;
        // 确保是3位数
        String orderCodeStr = String.format("%03d", orderCode);
        // 确保是1位数（实际上不影响结果，因为后面会截取）
        String genderCodeStr = String.valueOf(genderCode);
        // 前17位数字
        String baseCode = areaCode + birthDateStr + orderCodeStr;
        // 计算校验码
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += (baseCode.charAt(i) - '0') * WEIGHTS[i];
        }
        int mod = sum % 11;
        char checkCode = CHECK_CODES[mod];
        // 拼接完整的身份证号码
        return baseCode + checkCode;
    }
}
