package org.twt.ts.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.twt.ts.model.Account;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Integer> {

    Optional<Account> findAccountById(int id);

    Optional<Account> findAccountByUsername(String username);

    List<Account> findAccountsByIdIn(Collection<Integer> id);

}
