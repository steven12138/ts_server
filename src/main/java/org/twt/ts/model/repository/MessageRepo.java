package org.twt.ts.model.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.twt.ts.model.Account;
import org.twt.ts.model.Message;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MessageRepo extends JpaRepository<Message, String> {

    Optional<Message> findMessageById(String id);


    @Query("SELECT m FROM Message m WHERE m.sender IN ?1 AND NOT EXISTS (SELECT d FROM m.disabled d WHERE d=?2) order by m.dateTime desc ")
    List<Message> findMessagesBySenderIn(Collection<Account> sender, Account current,Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from Message m where m.id = ?1 and m.sender = ?2")
    void deleteMessageByIdAndSender(String id, Account sender);

    List<Message> findMessagesBySender(Account sender);
}
