package com.sar.fs.app.adapter.annotations;

import java.lang.annotation.Annotation;

/**
 * @auth: sarWang
 * @date: 2019-07-05 17:53
 * @describe
 */
public class ClassAnnotationParser {
    public ClassAnnotationParser() {
    }

    public static Integer getLayoutId(Class myClass) {
        Annotation annotation = myClass.getAnnotation(LayoutId.class);
        if (annotation instanceof LayoutId) {
            LayoutId layoutIdAnnotation = (LayoutId)annotation;
            return layoutIdAnnotation.value();
        } else {
            return null;
        }
    }
}

