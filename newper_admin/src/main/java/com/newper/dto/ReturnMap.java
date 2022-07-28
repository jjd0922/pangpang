package com.newper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/** ajax return map*/
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnMap {

    @Builder.Default
    private LocalDateTime timestamp=LocalDateTime.now();
    @Builder.Default
    private int status=200;
    private String message;

    /** location있는 경우 popup으로 열지*/
    private boolean isPopup;
    private String location;
    @Builder.Default
    Map<String, Object> data = new HashMap<>();

    public ReturnMap(String message) {
        this.message = message;
    }
    public void setMessage(String message){
        this.message=message;
    }
    public void setLocation(String location) { this.location=location; }

    public void put(String key, Object value) {
        data.put(key, value);
    }

    public Object get(String key) {
        return data.get(key);
    }
}
