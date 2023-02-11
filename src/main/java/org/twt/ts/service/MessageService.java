package org.twt.ts.service;

import org.twt.ts.exception.NoPrivilegesException;
import org.twt.ts.model.Message;

import java.util.List;

public interface MessageService {
    List<Message> getList() throws NoPrivilegesException;
}
