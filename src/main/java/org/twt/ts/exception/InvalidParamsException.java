package org.twt.ts.exception;

import org.twt.ts.utils.ReturnCode;

public class InvalidParamsException extends BaseException {
    public InvalidParamsException() {
        this.code = ReturnCode.InvalidParams;
    }
}
