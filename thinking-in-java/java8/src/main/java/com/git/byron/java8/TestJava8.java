package com.git.byron.java8;

/**
 * @Author: byron
 * @ProjectName: byron-demo-master
 * @Package: com.git.byron.java8
 * @ClassName: TestJava8
 * @Description:
 * @Date: 2019/8/12 10:17
 * @Version: 1.0
 */
public class TestJava8 {


    public static void main(String[] args){

        TestJava8Interface test = new TestJava8Interface() {
            @Override
            public double cater(int in) {
                return sfit(in);
            }
        };
       System.out.print( test.sfit(10));
    }
}
