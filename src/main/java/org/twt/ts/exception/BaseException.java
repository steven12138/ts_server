package org.twt.ts.exception;

import org.twt.ts.dto.Result;
import org.twt.ts.utils.ReturnCode;

public class BaseException extends Exception {
    protected ReturnCode code;

    public BaseException() {
        this.code = ReturnCode.UnknownFailed;
    }

    public Result getResult() {
        return Result.error(this.code);
    }
}
