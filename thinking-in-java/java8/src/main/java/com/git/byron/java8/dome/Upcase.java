package com.git.byron.java8.dome;

/**
 * @Author: byron
 * @ProjectName: byron-demo-master
 * @Package: com.git.byron.java8.dome
 * @ClassName: Upcase
 * @Description:
 * @Date: 2019/8/20 9:11
 * @Version: 1.0
 */
public class Upcase extends Processor {

    @Override
    String process(Object input) {
        return ((String)input).toUpperCase();
    }
}
