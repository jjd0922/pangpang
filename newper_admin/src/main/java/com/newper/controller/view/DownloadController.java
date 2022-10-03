package com.newper.controller.view;

import com.newper.storage.NewperStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;


@RequestMapping(value = "/download/")
@Controller
@RequiredArgsConstructor
public class DownloadController {

    /** 파일 다운로드 */
    @GetMapping(value = "")
    public void download(HttpServletResponse response, String obj, String fileName) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Disposition","attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
        NewperStorage.download(obj, response.getOutputStream());
    }
}
