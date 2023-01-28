package org.twt.ts.exception;

import org.twt.ts.utils.ReturnCode;

public class NoPrivilegesException extends AuthException{
    public NoPrivilegesException() {
        this.code = ReturnCode.NoPrivileges;
    }
}
