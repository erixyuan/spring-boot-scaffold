package com.eric.app.util;

import java.util.HashSet;
import java.util.List;

public class StrUtil {
    public static boolean hasDuplicateStrings(List<String> list) {
        HashSet<String> set = new HashSet<>();
        for (String str : list) {
            if (set.contains(str)) {
                return true;
            }
            set.add(str);
        }
        return false;
    }
}
