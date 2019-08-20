package com.git.byron.java8;

/**
 * @Author: byron
 * @ProjectName: byron-demo-master
 * @Package: com.git.byron.java8
 * @ClassName: Converter
 * @Description:
 * @Date: 2019/8/12 11:54
 * @Version: 1.0
 */
@FunctionalInterface
public interface Converter<F,T> {

    T convert(F from);
}
