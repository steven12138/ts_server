package org.twt.ts.controller;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.twt.ts.dto.Result;
import org.twt.ts.exception.NoPrivilegesException;
import org.twt.ts.model.Account;
import org.twt.ts.model.repository.AccountRepo;
import org.twt.ts.utils.FileTypeUtil;
import org.twt.ts.utils.ReturnCode;
import org.twt.ts.utils.UserInfoUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("info")
public class InfoController {


    @Resource
    private UserInfoUtil userInfoUtil;

    @GetMapping("{id}")
    public Result getInfo(@PathVariable String id) {
        return Result.success(userInfoUtil.getField(id));
    }

    @Value("${avatar.path}")
    private String filePath;


    @Value("${avatar.acceptExt}")
    private List<String> acceptExt;

    @Resource
    private AccountRepo accountRepo;

    @PostMapping("uploadAvatar")
    public Result uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException, NoPrivilegesException {
        if (file.isEmpty()) Result.error(ReturnCode.UnknownError);
        String ext = FileTypeUtil.getFileTypeBySuffix(Objects.requireNonNull(file.getOriginalFilename()));
        if (!acceptExt.contains(ext)) return Result.error(ReturnCode.InvalidFileType);
        Account target = userInfoUtil.getCurrent();
        String name = target.getId() + "." + ext;
        File dest = new File(filePath + "/" + name);
        file.transferTo(dest);
        target.setAvatar(name);
        accountRepo.save(target);
        return Result.success();
    }

    @GetMapping("avatar")
    public Result getAvatarPath() throws NoPrivilegesException {
        Account target = userInfoUtil.getCurrent();
        return Result.success(target.getAvatar());
    }

    @Transactional
    @PostMapping("nickname/{name}")
    public Result changeNickname(@PathVariable String name) throws NoPrivilegesException {
        Account t = userInfoUtil.getCurrent();
        t.setNickname(name);
        accountRepo.save(t);
        return Result.success();
    }

    @GetMapping("nickname/update")
    public Result changeNickname() throws NoPrivilegesException {
        Account t = userInfoUtil.getCurrent();
        return Result.success(t.getNickname());
    }

}
