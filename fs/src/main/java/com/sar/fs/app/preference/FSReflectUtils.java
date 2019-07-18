package com.sar.fs.app.preference;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * @auth: sarWang
 * @date: 2019-07-04 19:58
 * @describe
 */

public class FSReflectUtils {
    public FSReflectUtils() {
    }

    public static boolean isTransient(Field field) {
        return field.getAnnotation(FSTransparent.class) != null;
    }

    public static boolean isBaseDateType(Field field) {
        Class<?> clazz = field.getType();
        return clazz.equals(String.class) || clazz.equals(Integer.class) || clazz.equals(Byte.class) || clazz.equals(Long.class) || clazz.equals(Double.class) || clazz.equals(Float.class) || clazz.equals(Character.class) || clazz.equals(Short.class) || clazz.equals(Boolean.class) || clazz.equals(Date.class) || clazz.equals(Date.class) || clazz.equals(java.sql.Date.class) || clazz.isPrimitive();
    }

    public static String getFieldName(Field field) {
        FSField column = (FSField)field.getAnnotation(FSField.class);
        return column != null && column.name().trim().length() != 0 ? column.name() : field.getName();
    }
}

