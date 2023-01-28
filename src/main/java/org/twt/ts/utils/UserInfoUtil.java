package org.twt.ts.utils;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserInfoUtil {

    public Claims getUserInfo() {
        return (Claims) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public int getUserId() {
        return Integer.parseInt(getUserInfo().get("id").toString());
    }
}
