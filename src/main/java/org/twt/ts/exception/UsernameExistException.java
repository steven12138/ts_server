package org.twt.ts.exception;

import org.twt.ts.utils.ReturnCode;

public class UsernameExistException extends AuthException{
    public UsernameExistException() {
        this.code = ReturnCode.UsernameExist;
    }
}
