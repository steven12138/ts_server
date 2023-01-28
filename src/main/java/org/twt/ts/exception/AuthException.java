package org.twt.ts.exception;

import org.twt.ts.utils.ReturnCode;

public class AuthException extends BaseException{
    AuthException() {
        this.code = ReturnCode.NoPrivileges;
    }
}
