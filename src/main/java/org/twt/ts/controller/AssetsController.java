package org.twt.ts.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.twt.ts.exception.InvalidParamsException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/assets")
public class AssetsController {

    @Value("${avatar.path}")
    String avatarPath;

    @Value("${message.path}")
    String mediaPath;

    private byte[] getFileByte(String path) throws InvalidParamsException {
        File file = new File(path);
        try {
            InputStream stream = new FileInputStream(file);
            return stream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidParamsException();
        }
    }

    @GetMapping(
            value = "avatar/{path}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] avatar(@PathVariable(name = "path") String path) throws InvalidParamsException {
        return getFileByte(avatarPath + path);
    }

    @GetMapping(value = "media/{path}")
    public void getMedia(HttpServletResponse response, @PathVariable(name = "path") String path) throws InvalidParamsException, IOException {
        File file = new File(mediaPath + path);
        try {
            InputStream stream = new FileInputStream(file);
            response.setContentType("audio/peg3");
            response.setContentLength((int) file.length());
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + path);
            IOUtils.copy(stream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidParamsException();
        }
    }
}
