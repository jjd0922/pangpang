package com.newper.component;

import com.newper.exception.MsgException;
import com.newper.storage.NewperStorage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public class Common {
    /** 파일업로드 후 db저장할 str 반환 */
    public String uploadFilePath(MultipartFile mf, String pathPrefix) {
        try {
            String originalFilename = mf.getOriginalFilename();

            int index = originalFilename.lastIndexOf(".");
            String type = originalFilename.substring(index);

            String objectName = pathPrefix + UUID.randomUUID() + type;

            String path = NewperStorage.uploadFile(AdminBucket.SECRET, objectName, mf.getInputStream(), mf.getSize(), mf.getContentType());

            return path;
        } catch (IOException ioe){
            throw new MsgException("잠시 후 시도해주세요");
        }
    }
}
