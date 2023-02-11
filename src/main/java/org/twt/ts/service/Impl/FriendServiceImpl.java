package org.twt.ts.service.Impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.twt.ts.exception.NoPrivilegesException;
import org.twt.ts.model.Account;
import org.twt.ts.model.Friend;
import org.twt.ts.model.repository.FriendRepo;
import org.twt.ts.service.FriendService;
import org.twt.ts.utils.UserInfoUtil;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {
    @Resource
    private UserInfoUtil userInfoUtil;

    @Resource
    private FriendRepo friendRepo;

    @Override
    public List<Account> getFriendList() throws NoPrivilegesException {
        Account target = userInfoUtil.getCurrent();
        return friendRepo.findFriend(target.getId());
    }
}
