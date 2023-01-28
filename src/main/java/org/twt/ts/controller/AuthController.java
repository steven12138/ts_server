package org.twt.ts.controller;

import org.springframework.web.bind.annotation.*;
import org.twt.ts.dto.*;
import org.twt.ts.exception.*;
import org.twt.ts.service.AuthService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping("/login")
    Result login(@RequestBody User user) throws UsernamePasswordNotMatchException, UserForbiddenException {
        BasicUserInfo accountDetail = authService.login(user);
        return Result.success(accountDetail);
    }


    @PostMapping("/modifyPassword")
    Result modifyPassword(@RequestBody PasswordPair passwordPair) throws NoPrivilegesException, PasswordNotMatchException {
        authService.modifyPassword(passwordPair);
        return Result.success("Password Reset Success");
    }

    @GetMapping("/forgetPassword")
    Result getSecurityQuestion() throws NoPrivilegesException {
        Map<String, String> result = new HashMap<>();
        result.put("question", authService.getSecurityQuestion());
        return Result.success(result);
    }

    @PostMapping("/forgetPassword")
    Result resetPassword(@RequestBody SecurityAnswerPair pair) throws NoPrivilegesException, SecurityAnswerException {
        authService.modifyPassword(pair.getAnswer(), pair.getNewPassword());
        return Result.success("Password Reset Success");
    }

    @PostMapping("/register")
    Result register(@RequestBody RegisterUser user) throws UsernameExistException {
        authService.register(user);
        return Result.success("success");
    }

}
