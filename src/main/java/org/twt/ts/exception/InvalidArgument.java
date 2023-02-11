package org.twt.ts.exception;

import org.twt.ts.utils.ReturnCode;

public class InvalidArgument extends BaseException {
    public InvalidArgument() {
        this.code = ReturnCode.InvalidParams;
    }
}
