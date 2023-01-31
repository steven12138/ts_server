package org.twt.ts;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.twt.ts.model.Account;
import org.twt.ts.model.repository.AccountRepo;
import org.twt.ts.model.repository.FriendRepo;


@SpringBootTest
class TsApplicationTests {


    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    public void PasswordEncoder() {
        String target = "";
        String password = "123456";

        String encoded = passwordEncoder.encode(password);

        System.out.println(encoded);

        boolean result = passwordEncoder.matches(password, target);
        System.out.println(result);
    }

    @Resource
    private FriendRepo friendRepo;

    @Resource
    private AccountRepo accountRepo;

    @Test
    public void friendTester() {
        Account u = accountRepo.findAccountById(1).orElseThrow(UnknownError::new);
        Account v = accountRepo.findAccountById(2).orElseThrow(UnknownError::new);

        System.out.println(friendRepo.findFriend(1));
        friendRepo.remove(u, v);
    }

}
