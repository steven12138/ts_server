package org.twt.ts.exception;

import org.twt.ts.dto.ReturnCode;

public class AuthException extends BaseException{
    AuthException() {
        this.code = ReturnCode.NoPrivileges;
    }
}
