package org.twt.ts.service.Impl;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.twt.ts.dto.MessageInfo;
import org.twt.ts.exception.NoPrivilegesException;
import org.twt.ts.model.Account;
import org.twt.ts.model.Message;
import org.twt.ts.model.repository.AccountRepo;
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

    @Resource
    private AccountRepo accountRepo;

    @Override
    @Transactional
    public List<Message> getList() throws NoPrivilegesException {
        List<Account> friends = friendRepo.findFriend(userInfoUtil.getUserId());
        Account self = userInfoUtil.getCurrent();
        return messageRepo.findMessagesBySenderIn(friends).stream()
                .filter(e -> !e.getDisabled().contains(self))
                .toList();
    }

    @Override
    @Transactional
    public void sendMessage(String filename, MessageInfo info) throws NoPrivilegesException {
        List<Account> disabled_body = accountRepo.findAccountsByIdIn(info.getDisabled_id());
        Message message = Message.builder()
                .file(filename)
                .title(info.getTitle())
                .desc(info.getDescription())
                .sender(userInfoUtil.getCurrent())
                .disabled(disabled_body)
                .build();
        messageRepo.save(message);
    }

}
