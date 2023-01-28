package org.twt.ts.exception;

import org.twt.ts.utils.ReturnCode;

public class UsernamePasswordNotMatchException extends AuthException {
    public UsernamePasswordNotMatchException() {
        this.code = ReturnCode.UsernamePasswordNotMatch;
    }
}
