package org.twt.ts.exception;

import org.twt.ts.utils.ReturnCode;

public class PasswordNotMatchException extends AuthException {
    public PasswordNotMatchException() {
        this.code = ReturnCode.PasswordError;
    }
}
