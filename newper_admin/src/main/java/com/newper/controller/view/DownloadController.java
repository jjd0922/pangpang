package com.newper.controller.view;

import com.newper.dto.ParamMap;
import com.newper.exception.MsgException;
import com.newper.service.PoService;
import com.newper.storage.NewperStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;


@RequestMapping(value = "/download/")
@Controller
@RequiredArgsConstructor
public class DownloadController {
    private final PoService poService;

    /** 파일 다운로드 */
    @GetMapping(value = "")
    public void download(HttpServletResponse response, String obj, String fileName) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Disposition","attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
        NewperStorage.download(obj, response.getOutputStream());
    }
}
