package org.twt.ts.handler;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.twt.ts.dto.Result;
import org.twt.ts.utils.ReturnCode;
import org.twt.ts.exception.BaseException;

@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public Result tsExceptionHandler(BaseException e) {
        return e.getResult();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result InvalidMethod(HttpRequestMethodNotSupportedException e) {
        return Result.error(ReturnCode.InvalidMethod);
    }

    @ExceptionHandler(Exception.class)
    public Result BaseException(Exception e) {
        return new Result(
                ReturnCode.UnknownError,
                e.getClass().getName() + "\n" + e.getMessage()
        );
    }

}
