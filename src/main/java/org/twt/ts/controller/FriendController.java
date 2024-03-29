package org.twt.ts.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.twt.ts.dto.BaseID;
import org.twt.ts.dto.RequestInfo;
import org.twt.ts.dto.Result;
import org.twt.ts.exception.InvalidArgument;
import org.twt.ts.exception.NoPrivilegesException;
import org.twt.ts.exception.UsernameExistException;
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

    @GetMapping("/request")
    public Result getRequestList() throws NoPrivilegesException {
        return Result.success(friendService.getRequest());
    }

    @PostMapping("requestFriend")
    public Result requestFriend(@RequestBody RequestInfo info) throws NoPrivilegesException, UsernameExistException {
        friendService.requestFriend(info.getTo(), info.getDesc());
        return Result.success();
    }

    @GetMapping("search")
    public Result searchFriend(@RequestParam(name = "q") String keywords) {
        System.out.println(keywords);
        return Result.success(friendService.searchFriend(keywords));
    }


    @PostMapping("acceptRequest")
    public Result acceptRequest(@RequestBody BaseID requestId) throws InvalidArgument {
        friendService.acceptRequest(Integer.parseInt(requestId.getId()));
        return Result.success();
    }

    @PostMapping("remove")
    public Result remove(@RequestBody BaseID requestId) {
        friendService.removeFriend(Integer.parseInt(requestId.getId()));
        return Result.success();
    }

}
