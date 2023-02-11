package org.twt.ts.controller;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.twt.ts.dto.Result;
import org.twt.ts.exception.NoPrivilegesException;
import org.twt.ts.model.repository.AccountRepo;
import org.twt.ts.utils.UserInfoUtil;

import java.io.IOException;
import java.util.List;

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

    @Resource
    private UserInfoUtil userInfoUtil;
    @Resource
    private AccountRepo accountRepo;

    @PostMapping("send")
    @Transactional
    public Result sendMessage(@RequestParam("file") MultipartFile file) throws IOException, NoPrivilegesException {
//        if (file.isEmpty()) Result.error(ReturnCode.UnknownError);
//        String ext = FileTypeUtil.getFileTypeBySuffix(Objects.requireNonNull(file.getOriginalFilename()));
//        if (!acceptExt.contains(ext)) return Result.error(ReturnCode.InvalidParams);
//        Account target = userInfoUtil.getCurrent();
//        String name = target.getId() + "." + ext;
//        File dest = new File(filePath + "\\" + name);
//        file.transferTo(dest);
//        target.setAvatar(name);
//        accountRepo.save(target);
        return Result.success("success");
    }
}
