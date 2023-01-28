package org.twt.ts.exception;

import org.twt.ts.utils.ReturnCode;

public class SecurityAnswerException extends AuthException {
    public SecurityAnswerException() {
        this.code = ReturnCode.WrongSecurityAnswer;
    }
}
