package org.twt.ts.exception;

import org.twt.ts.utils.ReturnCode;

public class UserForbiddenException extends AuthException{
    public UserForbiddenException() {
        this.code = ReturnCode.UserForbidden;
    }
}
