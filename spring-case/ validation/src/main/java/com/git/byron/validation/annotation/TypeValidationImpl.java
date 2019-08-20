package com.git.byron.validation.annotation;

import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author: byron
 * @ProjectName: byron-demo-master
 * @Package: com.git.byron.validation
 * @ClassName: TypeValidationImpl
 * @Description: TypeValid 注解 校验器 实现
 * @Date: 2019/8/20 10:24
 * @Version: 1.0
 */
@Service
public class TypeValidationImpl implements ConstraintValidator<TypeValid,Object> {

    private int[] intType;
    private String[] strType;

    /**
     * 初始化方法， 在执行isValid 方法前，会先执行此方法
     *
     * @param typeValid
     *         注解信息模型，可以从该模型中获取注解类中定义的一些信息，如默认值等
     * @date 2019/1/19 11:27
     *
    ————————————————
    */
    @Override
    public void initialize(TypeValid typeValid) {
        this.intType = typeValid.intType();
        this.strType = typeValid.strType();
    }

    /**
     * 校验的具体逻辑实现
     * <p>
     * 注: 此方法可能会并发执行，需要根据实际情况看否是需要保证 线程安全
     *
     * @param object
     *         被自定义注解所标注的对象的 值
     * @param constraintValidatorContext
     *         Provides contextual data and operation when applying a given constraint validator.
     * @return 校验是否通过
     */
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        if(object instanceof String){
            for (String inte :strType){
                if(inte.equals(object)){
                    return true;
                }
            }
        }
        if(object instanceof Integer){
            for(Integer str:intType){
                if(str.equals(object)){
                    return true;
                }
            }
        }
        return false;
    }
}
