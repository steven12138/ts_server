package org.twt.ts.utils;

import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.twt.ts.exception.NoPrivilegesException;
import org.twt.ts.model.Account;
import org.twt.ts.model.repository.AccountRepo;


@Component
public class UserInfoUtil {

    @Resource
    private AccountRepo accountRepo;

    public Claims getUserInfo() {
        return (Claims) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String getField(String name) {
        return getUserInfo().get(name).toString();
    }

    public int getUserId() {
        return Integer.parseInt(getField("id"));
    }

    public String getUsername() {
        return getField("username");
    }

    public String getNickname() {
        return getField("nickname");
    }

    public String getPermission() {
        return getField("permission");
    }

    public Account getCurrent() throws NoPrivilegesException {
        return accountRepo.findAccountById(getUserId())
                .orElseThrow(NoPrivilegesException::new);
    }
}
