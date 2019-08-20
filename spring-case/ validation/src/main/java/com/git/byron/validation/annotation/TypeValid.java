package com.git.byron.validation.annotation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author: byron
 * @ProjectName: byron-demo-master
 * @Package: com.git.byron.validation
 * @ClassName: TypeValid
 * @Description:  自定义参数校验注解
        1、message、contains、payload是必须要写的
        2、还需要什么方法可根据自己的实际业务需求，自行添加定义即可
 * @Date: 2019/8/20 10:19
 * @Version: 1.0
 */

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = TypeValidationImpl.class)
public @interface TypeValid {

    int[] intType() default {};
    String[] strType() default {};

    // 当验证不通过时的提示信息
    String message() default "";

    // 根据实际需求定的方法
    String contains() default "";

    // 约束注解在验证时所属的组别
    Class<?>[] groups() default { };
    // 负载
    Class<? extends Payload>[] payload() default { };


    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
     @interface List {
       TypeValid[] value();
    }
}
