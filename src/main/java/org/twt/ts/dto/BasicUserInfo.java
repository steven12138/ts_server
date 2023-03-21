package org.twt.ts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasicUserInfo {
    private int id;
    private String username;
    private String nickname;
    private String token;
}
