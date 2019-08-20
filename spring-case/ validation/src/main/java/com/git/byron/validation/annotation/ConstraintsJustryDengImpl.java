package com.git.byron.validation.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author: byron
 * @ProjectName: byron-demo-master
 * @Package: com.git.byron.validation.annotation
 * @ClassName: ConstraintsJustryDengImpl
 * @Description:
 * @Date: 2019/8/20 16:58
 * @Version: 1.0
 */
public class ConstraintsJustryDengImpl implements ConstraintValidator<ConstraintsJustryDeng,Object> {

    private String str;

    @Override
    public void initialize(ConstraintsJustryDeng constraintsJustryDeng) {
        this.str = constraintsJustryDeng.str();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        if(object == null){
            return false;
        }
        if(object instanceof String){
            if(str.contains(String.valueOf(object))){
                return true;
            }
        }
        if(object instanceof Integer){
            if(str.contains(String.valueOf(object))){
                return true;
            }
        }
        return false;
    }
}
