# SpringBoot使用Validation校验参数

### 目录
准备工作

约束性注解(简单)说明

@Validated的使用时机

@Validated与@Valid的简单对比说明

自定义注解

对注解抛出的异常进行处理

###准备工作
引入相关依赖：
```java
          <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-validation</artifactId>
          </dependency>
 ```
###约束性注解(简单)说明

```java
            注解                          功能
            
            @AssertFalse                可以为null,如果不为null的话必须为false
            
            @AssertTrue                 可以为null,如果不为null的话必须为true
            
            @DecimalMax                 设置不能超过最大值
            
            @DecimalMin                 设置不能超过最小值
            
            @Digits                     设置必须是数字且数字整数的位数和小数的位数必须在指定范围内
            
            @Future                     日期必须在当前日期的未来
            
            @Past                       日期必须在当前日期的过去
            
            @Max                        最大不得超过此最大值
            
            @Min                        最大不得小于此最小值
            
            @NotNull                    不能为null，可以是空
            
            @Null                       必须为null
            
            @Pattern                    必须满足指定的正则表达式
            
            @Size                       集合、数组、map等的size()值必须在指定范围内
            
            @Email                      必须是email格式
            
            @Length                     长度必须在指定范围内
            
            @NotBlank                   字符串不能为null,字符串trin()后也不能等于“”
            
            @NotEmpty                   不能为null，集合、数组、map等size()不能为0；字符串trin()后可以等于“”
            
            @Range                       值必须在指定范围内
            
            @URL                        必须是一个URL
```

###@Validated的使用时机
    @Validated的使用位置较多(可详见源码)，但其主流的使用位置却是以下两种：
    
      ◎在Controller层中，放在模型参数对象前。
                当Controller层中参数是一个对象模型时，只有将@Validated直接放在该模型前，该模型内部的字段才会被
         校验(如果有对该模型的字段进行约束的话)。
         
      ◎在Controller层中，放在类上。
                 当一些约束是直接出现在Controller层中的参数前时，只有将@Validated放在类上时，参数前的约束才会生效。
                 
  #####  以下是简单的测试代码：
```java

/**
 * @Author: byron
 * @ProjectName: byron-demo-master
 * @Package: com.git.byron.validation.controller
 * @ClassName: UserController
 * @Description:  Controller层 --- 初步简单测试 @Validated 的使用位置
 * @Date: 2019/8/20 10:16
 * @Version: 1.0
 */
@RestController
@Validated
public class UserController {

    @GetMapping(value = "/user/type")
    public String  get(@Validated User user){
        return "用户类型是：" + user.getType();
    }

    @GetMapping(value = "/user/type2")
    public String  get2( @TypeValid(strType = {"1","2"},message = "不支持此类型") String type){
        return "用户类型是：" + type;
    }
}
```

####@Validated与@Valid的简单对比说明
    @Valid注解与@Validated注解功能大部分类似；两者的不同主要在于:@Valid属于javax下的，而@Validated属于spring下；@Valid支持嵌套校验、而@Validated不支持，@Validated支持分组，而@Valid不支持。笔者这里只简单介绍@Validated的使用时机。

###自定义注解
    虽然Bean Validation和Hibernate Validator已经提供了非常丰富的校验注解，但是在实际业务中，难免会碰到一些现有注解不足以校验的情况；这时，我们可以考虑自定义Validation注解。
 
####示例
第一步：创建自定义注解
```java

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
```
第二步：编写(第一步中的校验器实现类)该注解
```java


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
```

####对注解抛出的异常进行处理
    说明：当注解校验不通过时，直接将异常信息返回给前端其实并不友好，我们可以将异常包装一下，返回给前端。
    
    情况一：使用BindingResult类来容纳错误信息，当校验不通过时，不影响正常程
                  序往下走
    描述：当我们将@Validated注解放在参数模型前时，会校验该参数模型内的被标注了的字段。当校验不通过时
 
```java
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
```
###笔者寄语：
   本文只是对@Validated注解的简单探索，校验性注解用法较灵活，笔者后面有时间会进一步补充。
   技术学习只是理论上的，一些细节、一些坑还需要在实战中发现总结。