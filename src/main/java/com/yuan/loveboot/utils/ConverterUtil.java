package com.yuan.loveboot.utils;

import org.mapstruct.Named;

/**
 * @author Maverick
 */
public class ConverterUtil {
    @Named("int2Integer")
    public static Integer int2Integer(int i) {
        return i;
    }

    @Named("integer2Int")
    public static int integer2Int(Integer i) {
        return i;
    }
}
