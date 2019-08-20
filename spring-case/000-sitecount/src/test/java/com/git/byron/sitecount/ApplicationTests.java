package com.git.byron.sitecount;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.internal.Function;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Test
    public void contextLoads() {
       System.out.print(function.apply(1.0));
    }


    public static Function<Double,String> function = (e) -> {
        if(e == 1){
            return "Hello";
        }
        return "error";
    };
}
