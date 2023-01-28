package org.twt.ts;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

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

}
