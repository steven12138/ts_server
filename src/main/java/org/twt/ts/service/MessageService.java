package org.twt.ts.service;

import org.twt.ts.dto.MessageInfo;
import org.twt.ts.exception.NoPrivilegesException;
import org.twt.ts.model.Message;

import java.util.List;

public interface MessageService {
    List<Message> getList() throws NoPrivilegesException;

    void sendMessage(String filename, MessageInfo info) throws NoPrivilegesException;
}
