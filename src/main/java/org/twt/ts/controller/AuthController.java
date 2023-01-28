package org.twt.ts.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.twt.ts.dto.Result;
import org.twt.ts.dto.ReturnCode;

@RestController
@RequestMapping("auth")
public class AuthController {

    @PostMapping("login")
    Result login() {
        boolean success = true;
        if (success)
            return Result.success("Login Success");
        else
            return Result.error(ReturnCode.UsernamePasswordNotMatch);
    }
}
