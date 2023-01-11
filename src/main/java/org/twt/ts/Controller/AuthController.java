package org.twt.ts.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.twt.ts.DTO.Response;
import org.twt.ts.DTO.ReturnCode;

@RestController
@RequestMapping("auth")
public class AuthController {

    @PostMapping("login")
    Response login() {
        boolean success = true;
        if (success)
            return Response.success("Login Success");
        else
            return Response.error(ReturnCode.UsernamePasswordNotMatch);
    }
}
