package org.twt.ts.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.twt.ts.dto.Result;
import org.twt.ts.utils.UserInfoUtil;

@RestController
@RequestMapping("info")
public class InfoController {


    @Resource
    private UserInfoUtil userInfoUtil;

    @GetMapping("{id}")
    public Result getInfo(@PathVariable String id) {
        return Result.success(userInfoUtil.getField(id));
    }
}
