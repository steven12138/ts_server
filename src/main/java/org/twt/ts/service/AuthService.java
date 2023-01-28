package org.twt.ts.service;

import org.twt.ts.dto.BasicUserInfo;
import org.twt.ts.dto.RegisterUser;
import org.twt.ts.dto.User;
import org.twt.ts.exception.PasswordNotMatchException;
import org.twt.ts.exception.UserForbiddenException;
import org.twt.ts.exception.UsernamePasswordNotMatchException;

public interface AuthService {
    BasicUserInfo login(User user) throws UsernamePasswordNotMatchException, UserForbiddenException;


    void register(RegisterUser registerUser);

    void modifyPassword(String oldPassword, String newPassword) throws PasswordNotMatchException;

}
