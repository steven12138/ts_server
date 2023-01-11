package org.twt.ts.DTO;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private int code;
    private String message;
    private Object data;
    private long time_stamp;

    public Response(ReturnCode code, Object data) {
        this.time_stamp = System.currentTimeMillis();
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }


    public static Response success(Object data) {
        return new Response(ReturnCode.Success, data);
    }

    public static Response error(ReturnCode error) {
        return new Response(error, null);
    }
}
