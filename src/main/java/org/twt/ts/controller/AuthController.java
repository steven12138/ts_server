package org.twt.ts.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.twt.ts.dto.*;
import org.twt.ts.exception.*;
import org.twt.ts.service.AuthService;

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

    @GetMapping("/forgetPassword/{user}")
    Result getSecurityQuestion(@PathVariable String user) throws UserNotExistException {
        Map<String, String> result = new HashMap<>();
        result.put("question", authService.getSecurityQuestion(user));
        return Result.success(result);
    }

    @PostMapping("/forgetPassword/{user}")
    Result resetPassword(@RequestBody SecurityAnswerPair pair, @PathVariable String user)
            throws SecurityAnswerException, UserNotExistException {
        authService.modifyPassword(user, pair.getAnswer(), pair.getNewPassword());
        return Result.success("Password Reset Success");
    }

    @PostMapping("/register")
    Result register(@RequestBody RegisterUser user) throws UsernameExistException, InvalidArgument {
        authService.register(user);
        return Result.success();
    }

}
