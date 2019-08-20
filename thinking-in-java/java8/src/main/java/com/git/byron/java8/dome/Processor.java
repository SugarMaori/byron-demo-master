package com.git.byron.java8.dome;

/**
 * @Author: byron
 * @ProjectName: byron-demo-master
 * @Package: com.git.byron.java8.dome
 * @ClassName: Processor
 * @Description:
 * @Date: 2019/8/20 9:07
 * @Version: 1.0
 */
public class Processor {

    public String name(){
        return getClass().getSimpleName();
    }

    Object process(Object input){
        return input;
    }
}
