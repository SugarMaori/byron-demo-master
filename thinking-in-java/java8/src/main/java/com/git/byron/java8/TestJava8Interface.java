package com.git.byron.java8;

/**
 * @Author: byron
 * @ProjectName: byron-demo-master
 * @Package: com.git.byron.java8
 * @ClassName: TestJava8Interface
 * @Description:
 * @Date: 2019/8/12 10:07
 * @Version: 1.0
 */
public interface TestJava8Interface {

        public double cater(int in);

        default double sfit(int in){
            return Math.sqrt(in);
        }
}
