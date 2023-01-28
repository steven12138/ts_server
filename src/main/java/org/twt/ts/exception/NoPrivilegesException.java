package org.twt.ts.exception;

import org.twt.ts.dto.ReturnCode;

public class NoPrivilegesException extends AuthException{
    NoPrivilegesException() {
        this.code = ReturnCode.NoPrivileges;
    }
}
