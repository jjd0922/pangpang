package com.newper.storage;

/** 등록된 bucket list*/
public enum NewperBucket {

    /** 개발 공개 폴더*/
    DEV_OPEN("dev-open", true)
    /** 개발 비공개 폴더*/
    ,DEV_SECRET("dev-secret", false)

    ;

    private String bucketName;
    private boolean isOpen;

    NewperBucket(String bucketName, boolean isOpen) {
        this.bucketName = bucketName;
        this.isOpen = isOpen;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
