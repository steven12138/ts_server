package org.twt.ts.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.twt.ts.dto.Result;

@RestController()
@RequestMapping("/test")
public class ExperimentalController {

    @RequestMapping("/ping")
    public Result pong() {
        return Result.success("pong");
    }

    @PreAuthorize("hasAnyAuthority('1','2')")
    @RequestMapping("/permission")
    public Result testPermission(){
        return Result.success("you have the right permission");
    }
}
