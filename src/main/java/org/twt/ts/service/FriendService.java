package org.twt.ts.service;


import org.twt.ts.dto.ShortUser;
import org.twt.ts.exception.InvalidArgument;
import org.twt.ts.exception.NoPrivilegesException;
import org.twt.ts.exception.UsernameExistException;

import java.util.List;

public interface FriendService {
    List<ShortUser> getFriendList() throws NoPrivilegesException;

    void requestFriend(int id, String desc) throws NoPrivilegesException, UsernameExistException;

    void acceptRequest(int requestId) throws InvalidArgument;

    List<ShortUser> getRequest() throws NoPrivilegesException;

    List<ShortUser> searchFriend(String keywords);

    void removeFriend(int Target);
}
