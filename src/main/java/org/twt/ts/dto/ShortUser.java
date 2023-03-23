package org.twt.ts.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ShortUser {
    int id;
    String username;
    String nickname;
    String avatar;
}
