package org.twt.ts.exception;

import org.twt.ts.utils.ReturnCode;

public class InvalidFileExtException extends BaseException {
    public InvalidFileExtException() {
        this.code = ReturnCode.InvalidFileType;
    }
}
