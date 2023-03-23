package org.twt.ts.service;

import org.twt.ts.dto.BasicUserInfo;
import org.twt.ts.dto.PasswordPair;
import org.twt.ts.dto.RegisterUser;
import org.twt.ts.dto.User;
import org.twt.ts.exception.*;

public interface AuthService {
    BasicUserInfo login(User user) throws UsernamePasswordNotMatchException, UserForbiddenException;


    void register(RegisterUser registerUser) throws UsernameExistException, InvalidArgument;

    void modifyPassword(PasswordPair passwordPair) throws PasswordNotMatchException, NoPrivilegesException;

    void modifyPassword(String user, String securityAnswer, String newPassword) throws SecurityAnswerException, UserNotExistException;

    String getSecurityQuestion(String username) throws UserNotExistException;

}
