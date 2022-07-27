package com.newper.util;

import com.newper.storage.NewperBucket;
import com.newper.storage.NewperStorage;

public class NewperStorageTest {

    public static void main(String[] args){

        System.out.println("start");
        NewperStorage ns = new NewperStorage();
        ns.uploadFile(NewperBucket.DEV_OPEN, "test2/test.txt");
        System.out.println("end");
    }

}