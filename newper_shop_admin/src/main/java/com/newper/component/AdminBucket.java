package com.newper.component;

import com.newper.storage.NewperBucket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/** admin에서 사용하는 bucket*/
@Component
public class AdminBucket {

    public static NewperBucket OPEN;
    public static NewperBucket SECRET;

    @Value("${ncloud.bucket.open}")
    private String openBucket;
    @Value("${ncloud.bucket.secret}")
    private String secretBucket;

    @PostConstruct
    public void setBucket(){
        OPEN = NewperBucket.valueOf(openBucket);
        SECRET = NewperBucket.valueOf(secretBucket);
    }
}
