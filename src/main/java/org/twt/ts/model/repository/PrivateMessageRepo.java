package org.twt.ts.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.twt.ts.model.Account;
import org.twt.ts.model.PrivateMessage;

import java.util.List;

public interface PrivateMessageRepo extends JpaRepository<PrivateMessage, Integer> {
    List<PrivateMessage> findPrivateMessagesBySender(Account sender);

    List<PrivateMessage> findPrivateMessagesByReceiver(Account receiver);

}
