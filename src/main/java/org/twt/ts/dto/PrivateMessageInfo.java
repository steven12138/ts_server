package org.twt.ts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrivateMessageInfo {
    private String title;
    private String description;
    private int receiver_id;
}
