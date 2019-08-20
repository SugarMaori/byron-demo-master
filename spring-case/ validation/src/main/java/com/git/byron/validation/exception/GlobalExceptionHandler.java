package com.git.byron.validation.exception;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: byron
 * @ProjectName: byron-demo-master
 * @Package: com.git.byron.validation.exception
 * @ClassName: GlobalExceptionHandler
 * @Description:  统一异常处理

    注:@ControllerAdvice为Controller层增强器,其只能处理Controller层抛出的异常;
        由于代码间的层级调用机制  、异常的处理机制等,所以这里处理Controller层的异常,就相当于
        处理了全局异常

    注: @RestControllerAdvice等同于  @ResponseBody 加上 @ControllerAdvice

 * @Date: 2019/8/20 16:10
 * @Version: 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    public Map<String,String> globalExceptionHandleMethod(Exception ex){
        Map<String,String> map = new HashMap<>();
        if(ex instanceof ConstraintViolationException){
            ConstraintViolationException exception = (ConstraintViolationException)ex;
            map.put("msg","@Validated约束在类上：" + exception.getConstraintViolations());
            map.put("code","1");
        }else
        if(ex instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            BindingResult bindingResult  = exception.getBindingResult();
            if (bindingResult.hasErrors()){
                List<ObjectError> list = bindingResult.getAllErrors();
                for(ObjectError error:list){
                    map.put("msg", error.getDefaultMessage());
                }
            }
            map.put("code","2");
        }else if(ex instanceof BindException){
            BindException exception = (BindException) ex;
            BindingResult bindingResult  = exception.getBindingResult();
            if (bindingResult.hasErrors()){
                List<ObjectError> list = bindingResult.getAllErrors();
                for(ObjectError error:list){
                    map.put("msg",error.getDefaultMessage());
                }
            }
            map.put("code","3");
        }
        else{
            map.put("msg","系统异常:" + ex.getMessage() );
            map.put("code","4");
        }
        return map;
    }

}
