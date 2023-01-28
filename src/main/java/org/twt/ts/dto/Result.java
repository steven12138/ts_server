package org.twt.ts.dto;

import lombok.*;
import org.twt.ts.utils.ReturnCode;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private int code;
    private String message;
    private Object data;
    private long time_stamp;

    public Result(ReturnCode code, Object data) {
        this.time_stamp = System.currentTimeMillis();
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }


    public static Result success(Object data) {
        return new Result(ReturnCode.Success, data);
    }

    public static Result error(ReturnCode error) {
        return new Result(error, null);
    }
}
