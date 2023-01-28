package org.twt.ts.exception;

import org.twt.ts.dto.ReturnCode;

public class UserForbiddenException extends AuthException{
    public UserForbiddenException() {
        this.code = ReturnCode.UserForbidden;
    }
}
