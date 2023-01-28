package org.twt.ts.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.twt.ts.dto.BasicUserInfo;
import org.twt.ts.dto.Result;
import org.twt.ts.dto.User;
import org.twt.ts.exception.UserForbiddenException;
import org.twt.ts.exception.UsernamePasswordNotMatchException;
import org.twt.ts.service.AuthService;

import javax.annotation.Resource;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping("login")
    Result login(@RequestBody User user) {
        try {
            BasicUserInfo accountDetail = authService.login(user);
            return Result.success(accountDetail);
        } catch (UsernamePasswordNotMatchException | UserForbiddenException e) {
            return e.getResult();
        }
    }
}
