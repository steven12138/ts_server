package org.twt.ts.service;


import org.twt.ts.exception.InvalidArgument;
import org.twt.ts.exception.NoPrivilegesException;
import org.twt.ts.exception.UsernameExistException;
import org.twt.ts.model.Account;
import org.twt.ts.model.FriendRequest;

import java.util.List;

public interface FriendService {
    List<Account> getFriendList() throws NoPrivilegesException;

    void requestFriend(int id, String desc) throws NoPrivilegesException, UsernameExistException;

    void acceptRequest(int requestId) throws InvalidArgument;

    List<FriendRequest> getRequest() throws NoPrivilegesException;

    List<Account> searchFriend(String keywords);

    void removeFriend(int Target);
}
