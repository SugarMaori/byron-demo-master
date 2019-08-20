package com.git.byron.validation.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author: byron
 * @ProjectName: byron-demo-master
 * @Package: com.git.byron.validation.annotation
 * @ClassName: ConstraintsJustryDeng
 * @Description:
 * @Date: 2019/8/20 16:57
 * @Version: 1.0
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ConstraintsJustryDengImpl.class)
public @interface ConstraintsJustryDeng {

    String str() default "";

    // 当验证不通过时的提示信息
    String message() default "";

    // 根据实际需求定的方法
    String contains() default "";

    // 约束注解在验证时所属的组别
    Class<?>[] groups() default { };
    // 负载
    Class<? extends Payload>[] payload() default { };
}
