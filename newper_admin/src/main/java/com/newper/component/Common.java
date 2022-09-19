package com.newper.component;

import com.newper.dto.ParamMap;
import com.newper.exception.MsgException;
import com.newper.storage.NewperBucket;
import com.newper.storage.NewperStorage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

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

    public static String summernoteContent(String content){
        content=content.replaceAll("&lt;","<");
        content=content.replaceAll("&#37;","%");
        content=content.replaceAll("&gt;",">");
        content=content.replaceAll("&quot;","\"");
        content=content.replaceAll("<br>",System.getProperty("line.separator"));

        return content;
    }

    /** 발주품의 옵션값 map에 넣기  */
    public static void putOption(List<Map<String, Object>> optionList, List<String> optionStr) {
        for (int i = 0; i < optionStr.size(); i++) {
            if (!optionStr.get(i).equals("")) {
                String [] option_str = optionStr.get(i).split(":");
                Map<String, Object> option = new HashMap<>();
                option.put("title", option_str[0]);
                option.put("values", option_str[1]);
                optionList.add(option);
            }
        }
    }
}
