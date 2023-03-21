package org.twt.ts.service.Impl;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.twt.ts.dto.MessageInfo;
import org.twt.ts.dto.PrivateMessageInfo;
import org.twt.ts.exception.NoPrivilegesException;
import org.twt.ts.exception.UserNotExistException;
import org.twt.ts.model.Account;
import org.twt.ts.model.Message;
import org.twt.ts.model.PrivateMessage;
import org.twt.ts.model.repository.AccountRepo;
import org.twt.ts.model.repository.FriendRepo;
import org.twt.ts.model.repository.MessageRepo;
import org.twt.ts.model.repository.PrivateMessageRepo;
import org.twt.ts.service.MessageService;
import org.twt.ts.utils.UserInfoUtil;

import java.util.List;

@Service
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

    @Resource
    private PrivateMessageRepo privateMessageRepo;

    @Override
    public void sendPrivateMessage(String filename, PrivateMessageInfo info) throws NoPrivilegesException, UserNotExistException {
        PrivateMessage message = PrivateMessage.builder()
                .file(filename)
                .title(info.getTitle())
                .desc(info.getDescription())
                .sender(userInfoUtil.getCurrent())
                .receiver(
                        accountRepo.findAccountById(info.getReceiver_id())
                                .orElseThrow(UserNotExistException::new)
                )
                .build();
        privateMessageRepo.save(message);
    }

    @Override
    public List<PrivateMessage> getPrivateList() throws NoPrivilegesException {
        return privateMessageRepo.findPrivateMessagesByReceiver(userInfoUtil.getCurrent());
    }

}
