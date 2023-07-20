package com.eric.app.util;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CodeUtil
{
    public static String generateCode(String prefix) {
        String date = getFormatTime(LocalDateTime.now());
        SecureRandom random = new SecureRandom();
        int r = random.nextInt(1000);
        String code = String.format("%s%s%03d", prefix, date, r);
        return code;
    }

    private static String getFormatTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return dateTime.format(formatter);
    }
}
