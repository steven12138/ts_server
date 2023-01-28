package org.twt.ts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordPair {
    private String oldPassword;
    private String newPassword;
}
