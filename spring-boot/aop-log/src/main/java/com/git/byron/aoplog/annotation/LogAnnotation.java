package com.git.byron.aoplog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: byron
 * @ProjectName: byron-demo-master
 * @Package: com.git.byron.aoplog.annotation
 * @ClassName: LogAnnotation
 * @Description:
 * @Date: 2019/8/8 15:22
 * @Version: 1.0
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {
}
