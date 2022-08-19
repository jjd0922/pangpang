package com.newper.component;

import com.newper.dto.ParamMap;
import com.newper.exception.MsgException;
import com.newper.storage.NewperBucket;
import com.newper.storage.NewperStorage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class Common {
    /** 파일업로드 후 db저장할 str 반환 */
    public static String uploadFilePath(MultipartFile mf, String pathPrefix, NewperBucket nb) {
        try {
            String originalFilename = mf.getOriginalFilename();

            int index = originalFilename.lastIndexOf(".");
            String type = originalFilename.substring(index);

            String objectName = pathPrefix + UUID.randomUUID() + type;

            String path = NewperStorage.uploadFile(nb, objectName, mf.getInputStream(), mf.getSize(), mf.getContentType());

            return path;
        } catch (IOException ioe){
            throw new MsgException("잠시 후 시도해주세요");
        }
    }

    /** 배열 괄호 제거 */
    public static void changeArr(ParamMap paramMap, String key) {
        String value = paramMap.getMap().get(key).toString();
        value = value.replace("[", "");
        value = value.replace("]", "");

        String[] value_arr = value.split(", ");

        paramMap.getMap().put(key, value_arr);
    }

    public static String summernoteContent(String content){
        content=content.replaceAll("&lt;","<");
        content=content.replaceAll("&#37;","%");
        content=content.replaceAll("&gt;",">");
        content=content.replaceAll("&quot;","\"");
        content=content.replaceAll("<br>",System.getProperty("line.separator"));

        return content;
    }

}
