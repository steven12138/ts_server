package org.twt.ts.exception;

import org.twt.ts.dto.ReturnCode;

public class PasswordNotMatchException extends AuthException {
    public PasswordNotMatchException() {
        this.code = ReturnCode.PasswordError;
    }
}
