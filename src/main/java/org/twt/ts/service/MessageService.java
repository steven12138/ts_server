package org.twt.ts.service;

import jakarta.transaction.Transactional;
import org.twt.ts.dto.MessageInfo;
import org.twt.ts.dto.PrivateMessageInfo;
import org.twt.ts.dto.ShortUser;
import org.twt.ts.exception.InvalidParamsException;
import org.twt.ts.exception.NoPrivilegesException;
import org.twt.ts.exception.UserNotExistException;
import org.twt.ts.model.Message;
import org.twt.ts.model.PrivateMessage;

import java.util.List;

public interface MessageService {

    @Transactional
    List<Message> getList(int page) throws NoPrivilegesException;

    void sendMessage(String filename, MessageInfo info) throws NoPrivilegesException;

    void sendPrivateMessage(String filename, PrivateMessageInfo info) throws NoPrivilegesException, UserNotExistException;

    List<PrivateMessage> getPrivateList() throws NoPrivilegesException;

    List<ShortUser> getLikesById(String id) throws InvalidParamsException;

    void updateLikes(String id) throws InvalidParamsException, NoPrivilegesException;

    boolean isLiked(String id) throws InvalidParamsException, NoPrivilegesException;

    void deleteMessage(String id) throws NoPrivilegesException;

    void deletePrivateMessage(String id);

    List<Message> getMine() throws NoPrivilegesException;
}
