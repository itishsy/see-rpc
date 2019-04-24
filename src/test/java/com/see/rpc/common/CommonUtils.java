package com.see.rpc.common;

public class CommonUtils {

    public static boolean equals(Long v1 , Integer v2) {
        if (null != v1 && null != v2)
            return v1.intValue() == v2;
        return false;
    }

    public static boolean equals(Long v1 , String v2) {
        if (null != v1 && null != v2)
            return v1.intValue() == new Long(v2).intValue();
        return false;
    }

    public static boolean equals(Integer v1 , String v2) {
        if (null != v1 && null != v2)
            return v1 == new Long(v2).intValue();
        return false;
    }
}
