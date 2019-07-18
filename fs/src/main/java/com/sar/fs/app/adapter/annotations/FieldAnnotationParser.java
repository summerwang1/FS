package com.sar.fs.app.adapter.annotations;

import android.app.Activity;
import android.view.View;
import java.lang.reflect.Field;
/**
 * @auth: sarWang
 * @date: 2019-07-05 17:54
 * @describe
 */
public class FieldAnnotationParser {
    public FieldAnnotationParser() {
    }

    public static void setViewFields(Object object, final View view) {
        setViewFields(object, new FieldAnnotationParser.ViewFinder() {
            public View findViewById(int viewId) {
                return view.findViewById(viewId);
            }
        });
    }

    private static void setViewFields(Object object, FieldAnnotationParser.ViewFinder viewFinder) {
        Field[] fields = object.getClass().getDeclaredFields();
        Field[] var3 = fields;
        int var4 = fields.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Field field = var3[var5];
            if (field.isAnnotationPresent(ViewId.class)) {
                field.setAccessible(true);
                ViewId viewIdAnnotation = field.getAnnotation(ViewId.class);

                try {
                    field.set(object, field.getType().cast(viewFinder.findViewById(viewIdAnnotation.value())));
                } catch (IllegalAccessException var9) {
                    var9.printStackTrace();
                }
            }
        }

    }

    public static void setViewFields(Object object, final Activity activity) {
        setViewFields(object, new FieldAnnotationParser.ViewFinder() {
            public View findViewById(int viewId) {
                return activity.findViewById(viewId);
            }
        });
    }

    private interface ViewFinder {
        View findViewById(int var1);
    }
}

