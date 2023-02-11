package org.twt.ts.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.twt.ts.dto.Result;
import org.twt.ts.exception.NoPrivilegesException;
import org.twt.ts.service.FriendService;

@RestController
@RequestMapping("friend")
public class FriendController {

    @Resource
    private FriendService friendService;

    @GetMapping("/list")
    public Result getFriendList() throws NoPrivilegesException {

        return Result.success(friendService.getFriendList());
    }

    @PostMapping("requestFriend/{id}")
    public Result requestFriend(@PathVariable String id) {
        return Result.success("");
    }
}
