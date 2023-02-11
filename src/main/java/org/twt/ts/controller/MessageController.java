package org.twt.ts.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.twt.ts.dto.Result;
import org.twt.ts.utils.FileTypeUtil;
import org.twt.ts.utils.ReturnCode;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("message")
public class MessageController {
    @GetMapping("list")
    public Result getList() {
        return Result.success("");
    }

    @Value("${message.path}")
    private String filePath;


    @Value("${message.acceptExt}")
    private List<String> acceptExt;

    @PostMapping("send")
    public Result sendMessage(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) Result.error(ReturnCode.UnknownError);
        String ext = FileTypeUtil.getFileTypeBySuffix(Objects.requireNonNull(file.getOriginalFilename()));
        System.out.println(ext);
        if (!acceptExt.contains(ext)) return Result.error(ReturnCode.InvalidParams);
        String name = UUID.randomUUID() + "." + ext;
        String pathName = filePath + name;
        System.out.println(pathName);
        File dest = new File(filePath + name);
        file.transferTo(dest);
        return Result.success("success");
    }
}
