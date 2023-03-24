package org.twt.ts.service.Impl;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.twt.ts.dto.MessageInfo;
import org.twt.ts.dto.PrivateMessageInfo;
import org.twt.ts.dto.ShortUser;
import org.twt.ts.exception.InvalidParamsException;
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
    public List<Message> getList(int page) throws NoPrivilegesException {
        List<Account> friends = friendRepo.findFriend(userInfoUtil.getUserId());
        friends.add(userInfoUtil.getCurrent());
        System.out.println(friends);
        return messageRepo.findMessagesBySenderIn(friends, userInfoUtil.getCurrent(), PageRequest.of(
                page, 10
        ));
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

    @Override
    public List<ShortUser> getLikesById(String id) throws InvalidParamsException {
        Message target = messageRepo.findMessageById(id).orElseThrow(InvalidParamsException::new);
        return target.getLikes().stream().map(e -> new ShortUser(e.getId(), e.getUsername(), e.getNickname(), e.getAvatar())).toList();
    }

    @Override
    @Transactional
    public void updateLikes(String id) throws InvalidParamsException, NoPrivilegesException {
        Message target = messageRepo.findMessageById(id).orElseThrow(InvalidParamsException::new);
        Account self = userInfoUtil.getCurrent();
        List<Account> likes = target.getLikes();
        System.out.println(likes);
        if (!likes.contains(self)) {
            System.out.println("add");
            likes.add(self);
        } else {
            System.out.println("stop");
            likes.remove(self);
        }
        target.setLikes(likes);
        messageRepo.save(target);
    }

    @Override
    public boolean isLiked(String id) throws InvalidParamsException, NoPrivilegesException {
        return messageRepo
                .findMessageById(id)
                .orElseThrow(InvalidParamsException::new)
                .getLikes()
                .contains(userInfoUtil.getCurrent());
    }

    @Override
    public void deleteMessage(String id) throws NoPrivilegesException {
        messageRepo.deleteMessageByIdAndSender(id, userInfoUtil.getCurrent());
    }

    @Override
    public void deletePrivateMessage(String id) {
        privateMessageRepo.deletePrivateMessageByPid(id);
    }

    @Override
    public List<Message> getMine() throws NoPrivilegesException {
        return messageRepo.findMessagesBySender(userInfoUtil.getCurrent());
    }


}
