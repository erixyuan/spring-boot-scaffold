package com.eric.app.util;

public class ParamUtil {

    public static String wrap(String p){
        return "'" + p + "'";
    }
    public static String wrapLike(String p){
        return "'%" + p + "%'";
    }
}
