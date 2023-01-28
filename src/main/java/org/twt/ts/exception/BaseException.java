package org.twt.ts.exception;

import org.twt.ts.dto.Result;
import org.twt.ts.dto.ReturnCode;

public class BaseException extends Exception {
    protected ReturnCode code;

    public Result getResult() {
        return Result.error(this.code);
    }
}
