package com.mxchip.helper;

/**
 * Created by Rocke on 2016/06/12.
 */
public class CheckHelper {
    /**
     * 判断是否为空
     *
     * @param param
     * @return
     */
    public static boolean checkPara(String... param) {
        if (null == param || param.equals("")) {
            return false;
        } else if (param.length > 0) {
            for (String str : param) {
                if (null == str || str.equals("") || str.equals("null") || str.equals("undefined")) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
