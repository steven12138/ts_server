package org.twt.ts.Controller;

import org.springframework.web.bind.annotation.*;
import org.twt.ts.DTO.HiDTO;
import org.twt.ts.DTO.Response;

@RestController
public class BaseController {

    @GetMapping("/hello")
    Response hello() {
        return Response.success("Hello World From Spring Boot");
    }

    @PostMapping("/hi")
    Response hi(@RequestBody HiDTO data) {
        System.out.println(data.getMessage());
        return Response.success("your name is " + data.getName());
    }
}
