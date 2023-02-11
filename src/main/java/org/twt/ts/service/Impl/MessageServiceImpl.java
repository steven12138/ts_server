package org.twt.ts.service.Impl;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.twt.ts.exception.NoPrivilegesException;
import org.twt.ts.model.Account;
import org.twt.ts.model.Message;
import org.twt.ts.model.repository.FriendRepo;
import org.twt.ts.model.repository.MessageRepo;
import org.twt.ts.service.MessageService;
import org.twt.ts.utils.UserInfoUtil;

import java.util.List;

public class MessageServiceImpl implements MessageService {

    @Resource
    private FriendRepo friendRepo;

    @Resource
    private MessageRepo messageRepo;

    @Resource
    private UserInfoUtil userInfoUtil;

    @Override
    @Transactional
    public List<Message> getList() throws NoPrivilegesException {
        List<Account> friends = friendRepo.findFriend(userInfoUtil.getUserId());
        Account self = userInfoUtil.getCurrent();
        return messageRepo.findMessagesBySenderIn(friends).stream()
                .filter(e -> !e.getDisabled().contains(self))
                .toList();
    }
}
