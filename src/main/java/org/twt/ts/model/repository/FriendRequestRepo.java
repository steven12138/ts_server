package org.twt.ts.model.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.twt.ts.dto.ShortUser;
import org.twt.ts.model.Account;
import org.twt.ts.model.FriendRequest;

import java.util.List;

public interface FriendRequestRepo extends JpaRepository<FriendRequest, Integer> {

    @Query("select new org.twt.ts.dto.ShortUser(f.rid,f.from.username,f.from.nickname,f.from.avatar) from FriendRequest f where f.to= ?1 and f.accept = false ")
    List<ShortUser> findRequest(Account from);

    List<FriendRequest> findFriendRequestsByFromAndAcceptIsFalse(Account from);

    @Modifying
    @Transactional
    @Query("update FriendRequest f set f.accept=true where f.from=?1 and f.to=?2")
    void acceptFriend(Account from, Account to);
}
