package org.twt.ts.service;

import org.twt.ts.dto.MessageInfo;
import org.twt.ts.dto.PrivateMessageInfo;
import org.twt.ts.exception.InvalidParamsException;
import org.twt.ts.exception.NoPrivilegesException;
import org.twt.ts.exception.UserNotExistException;
import org.twt.ts.model.Account;
import org.twt.ts.model.Message;
import org.twt.ts.model.PrivateMessage;

import java.util.List;

public interface MessageService {
    List<Message> getList() throws NoPrivilegesException;

    void sendMessage(String filename, MessageInfo info) throws NoPrivilegesException;

    void sendPrivateMessage(String filename, PrivateMessageInfo info) throws NoPrivilegesException, UserNotExistException;

    List<PrivateMessage> getPrivateList() throws NoPrivilegesException;

    List<Account> getLikesById(String id) throws InvalidParamsException;

    void updateLikes(String id) throws InvalidParamsException, NoPrivilegesException;

    boolean isLiked(String id) throws InvalidParamsException, NoPrivilegesException;
}
