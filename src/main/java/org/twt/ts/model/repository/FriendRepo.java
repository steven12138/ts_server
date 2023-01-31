package org.twt.ts.model.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.twt.ts.model.Account;
import org.twt.ts.model.Friend;

import java.util.List;

public interface FriendRepo extends JpaRepository<Friend, Integer> {

    @Query(value = "select friend.v from Friend friend where friend.u.id=?1")
    List<Account> findFriend(int id);

    @Transactional
    @Modifying
    @Query(value = "" +
            "INSERT INTO friend (`u`, `v`) SELECT ?1, ?2 FROM DUAL WHERE NOT EXISTS (SELECT `u`, `v` FROM friend WHERE u = ?1 and v = ?2)", nativeQuery = true)
    void _add(int u, int v);

    @Transactional
    @Modifying
    @Query(value = "delete from friend where `u` = ?1 and `v` = ?2", nativeQuery = true)
    void _delete(int u, int v);

    default void add(int u, int v) {
        _add(u, v);
        _add(v, u);
    }

    default void add(Account u, Account v) {
        _add(u.getId(), v.getId());
        _add(v.getId(), u.getId());
    }

    default void remove(int u, int v) {
        _delete(u, v);
        _delete(v, u);
    }

    default void remove(Account u, Account v) {
        _delete(u.getId(), v.getId());
        _delete(v.getId(), u.getId());
    }
}
