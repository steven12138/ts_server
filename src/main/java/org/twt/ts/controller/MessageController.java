package org.twt.ts.controller;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.twt.ts.dto.MessageInfo;
import org.twt.ts.dto.Result;
import org.twt.ts.exception.NoPrivilegesException;
import org.twt.ts.model.repository.AccountRepo;
import org.twt.ts.service.MessageService;
import org.twt.ts.utils.FileTypeUtil;
import org.twt.ts.utils.ReturnCode;
import org.twt.ts.utils.UserInfoUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("message")
public class MessageController {

    @Resource
    private MessageService messageService;

    @GetMapping("list")
    public Result getList() throws NoPrivilegesException {
        return Result.success(messageService.getList());
    }

    @Value("${message.path}")
    private String filePath;


    @Value("${message.acceptExt}")
    private List<String> acceptExt;

    @Resource
    private UserInfoUtil userInfoUtil;
    @Resource
    private AccountRepo accountRepo;

    @PostMapping("send")
    @Transactional
    public Result sendMessage(@RequestParam("file") MultipartFile file, @RequestParam("data") MessageInfo messageInfo) throws IOException, NoPrivilegesException {
        if (file.isEmpty()) Result.error(ReturnCode.UnknownError);
        String ext = FileTypeUtil.getFileTypeBySuffix(Objects.requireNonNull(file.getOriginalFilename()));
        if (!acceptExt.contains(ext)) return Result.error(ReturnCode.InvalidParams);
        String name = UUID.randomUUID() + "." + ext;
        File dest = new File(filePath + "/" + name);
        file.transferTo(dest);

        messageService.sendMessage(name, messageInfo);

        return Result.success("success");
    }


}
