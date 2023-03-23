package org.twt.ts.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.twt.ts.dto.ShortUser;
import org.twt.ts.model.Account;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Integer> {

    Optional<Account> findAccountById(int id);

    Optional<Account> findAccountByUsername(String username);

    List<Account> findAccountsByIdIn(Collection<Integer> id);

    @Query("select new org.twt.ts.dto.ShortUser(a.id, a.username,a.nickname,a.avatar) from Account a where( a.nickname like %?1% or a.username like %?1% )and a.id!=?2")
    List<ShortUser> searchAccount(String pattern, int id);

}
