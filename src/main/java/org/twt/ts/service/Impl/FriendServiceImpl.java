package org.twt.ts.service.Impl;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.twt.ts.dto.ShortUser;
import org.twt.ts.exception.InvalidArgument;
import org.twt.ts.exception.NoPrivilegesException;
import org.twt.ts.exception.UsernameExistException;
import org.twt.ts.model.Account;
import org.twt.ts.model.FriendRequest;
import org.twt.ts.model.repository.AccountRepo;
import org.twt.ts.model.repository.FriendRepo;
import org.twt.ts.model.repository.FriendRequestRepo;
import org.twt.ts.service.FriendService;
import org.twt.ts.utils.UserInfoUtil;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {
    @Resource
    private UserInfoUtil userInfoUtil;

    @Resource
    private FriendRepo friendRepo;
    @Resource
    private AccountRepo accountRepo;

    @Resource
    private FriendRequestRepo requestRepo;

    @Override
    public List<ShortUser> getFriendList() throws NoPrivilegesException {
        Account target = userInfoUtil.getCurrent();
        return friendRepo.findFriendShort(target.getId());
    }

    @Override
    @Transactional
    public void requestFriend(int id, String desc) throws NoPrivilegesException, UsernameExistException {
        Account self = userInfoUtil.getCurrent();
        Account to = accountRepo.findAccountById(id).orElseThrow(UsernameExistException::new);
        FriendRequest request = FriendRequest.builder()
                .desc(desc)
                .from(self)
                .to(to)
                .build();
        requestRepo.save(request);
    }

    @Override
    @Transactional
    public void acceptRequest(int requestId) throws InvalidArgument {
        FriendRequest target = requestRepo.findById(requestId)
                .orElseThrow(InvalidArgument::new);
        target.setAccept(true);
        friendRepo.add(target.getFrom(), target.getTo());
    }

    @Override
    public List<ShortUser> getRequest() throws NoPrivilegesException {
        return requestRepo.findRequest(userInfoUtil.getCurrent());
    }

    @Override
    public List<ShortUser> searchFriend(String keywords) {
        System.out.println(userInfoUtil.getUserId());
        return accountRepo.searchAccount(keywords, userInfoUtil.getUserId());
    }

    @Override
    public void removeFriend(int target) {
        friendRepo.remove(userInfoUtil.getUserId(), target);
    }
}
