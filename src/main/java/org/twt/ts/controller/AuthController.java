package org.twt.ts.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.twt.ts.dto.*;
import org.twt.ts.exception.*;
import org.twt.ts.service.AuthService;
import org.twt.ts.utils.ReturnCode;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping("/login")
    Result login(@RequestBody User user) throws UsernamePasswordNotMatchException, UserForbiddenException {
        System.out.println(user);
        BasicUserInfo accountDetail = authService.login(user);
        return Result.success(accountDetail);
    }


    @PostMapping("/modifyPassword")
    Result modifyPassword(@RequestBody PasswordPair passwordPair) throws NoPrivilegesException, PasswordNotMatchException {
        authService.modifyPassword(passwordPair);
        return Result.success("Password Reset Success");
    }

    @GetMapping("/forgetPassword/{id}")
    Result getSecurityQuestion(@PathVariable int id) throws UsernameExistException {
        Map<String, String> result = new HashMap<>();
        result.put("question", authService.getSecurityQuestion(id));
        return Result.success(result);
    }

    @PostMapping("/forgetPassword/{id}")
    Result resetPassword(@RequestBody SecurityAnswerPair pair, @PathVariable int id) throws SecurityAnswerException, UsernameExistException {
        authService.modifyPassword(id, pair.getAnswer(), pair.getNewPassword());
        return Result.success("Password Reset Success");
    }

    @PostMapping("/register")
    Result register(@RequestBody RegisterUser user) throws UsernameExistException {
        authService.register(user);
        return Result.success("success");
    }

}
