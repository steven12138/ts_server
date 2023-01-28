package org.twt.ts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SecurityAnswerPair {
    private String answer;
    private String newPassword;
}
