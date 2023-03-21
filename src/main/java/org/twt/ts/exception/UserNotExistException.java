package org.twt.ts.exception;

import org.twt.ts.utils.ReturnCode;

public class UserNotExistException extends BaseException {
    public UserNotExistException() {
        this.code = ReturnCode.UserNotExist;
    }
}
