package org.twt.ts.service;


import org.twt.ts.exception.NoPrivilegesException;
import org.twt.ts.model.Account;
import org.twt.ts.model.FriendRequest;

import java.util.List;

public interface FriendService {
    List<Account> getFriendList() throws NoPrivilegesException;

    void requestFriend(int id);

    void acceptRequest(int id);

    List<FriendRequest> getRequest();
}
