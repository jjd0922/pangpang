package com.newper.util;

import com.newper.storage.NewperBucket;
import com.newper.storage.NewperStorage;

public class NewperStorageTest {

    public static void main(String[] args){

        String bucket_objectName = "dev-secret/test/1234.txt";
        int bucketIndex = bucket_objectName.indexOf("/");
        System.out.println(bucketIndex);

        String bucketName = bucket_objectName.substring(0, bucketIndex);
        System.out.println(bucketName);
        String objectName = bucket_objectName.substring(bucketIndex + 1);
        System.out.println(objectName);

    }

}