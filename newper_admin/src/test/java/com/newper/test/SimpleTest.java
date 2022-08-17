package com.newper.test;

import org.junit.jupiter.api.Test;
import org.springframework.util.PatternMatchUtils;

import java.util.regex.Pattern;

public class SimpleTest {


    @Test
    public void test(){
        String uId="";
        System.out.println(Pattern.matches("^[a-zA-Z0-9]*$", uId));

    }
}
