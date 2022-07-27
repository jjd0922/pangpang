package com.newper.storage;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** NCLoud storage사용. AWS S3 와 같은 방식*/
public class NewperStorage {

    private AmazonS3 getBucket(NewperBucket newperBucket){
        String endPoint = "https://kr.object.ncloudstorage.com";
        String regionName = "kr-standard";
        String accessKey = "7wuWOBJeItCBZbu52uuW";
        String secretKey = "UavKkQ50pfkYzULsBV4oCQfKm7Mll322VbhGKgGF";

        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();

        // create bucket if the bucket name does not exist
        if (!s3.doesBucketExistV2(newperBucket.getBucketName())) {
            s3.createBucket(newperBucket.getBucketName());
        }
        return s3;
    }


    /** 파일 업로드. objectName은 폴더/파일명으로 . 폴더는 TABLE명 , 파일은 IDX_UUID사용. 파일명 겹치는 경우 덮어씀  */
    public String uploadFile(NewperBucket newperBucket, String objectName){
        String bucketName = newperBucket.getBucketName();
        AmazonS3 s3 = getBucket(newperBucket);

        s3.putObject(bucketName, objectName, new File("K:/test123.txt"));

        //접근 권한 설정
        if (newperBucket.isOpen()) {
            AccessControlList accessControlList = s3.getObjectAcl(bucketName, objectName);
            // add read permission to user by ID
            accessControlList.grantPermission(GroupGrantee.AllUsers, Permission.Read);
            s3.setObjectAcl(bucketName, objectName, accessControlList);

            return "https://kr.object.ncloudstorage.com/"+objectName;
        }else{
            return objectName;
        }

    }

    /** 비공개 파일 다운로드*/
    public void download(NewperBucket newperBucket, String objectName, OutputStream outputStream){
        String bucketName = newperBucket.getBucketName();
        AmazonS3 s3 = getBucket(newperBucket);
        S3Object s3Object = s3.getObject(bucketName, objectName);
        S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();

//        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFilePath));
        try{
            byte[] bytesArray = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = s3ObjectInputStream.read(bytesArray)) != -1) {
                outputStream.write(bytesArray, 0, bytesRead);
            }

            outputStream.close();
            s3ObjectInputStream.close();
        }catch (IOException ioe){

        }
    }
}
