package com.newper.test;

import org.junit.jupiter.api.Test;

public class SimpleTest {


    @Test
    public void test(){
        byte  t = 1;
        Byte test = new Byte(t);

        if(test == 1){
            System.out.println("is truee");
        }else{
            System.out.println("222");
        }

    }
}
