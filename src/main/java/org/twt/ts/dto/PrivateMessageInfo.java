package org.twt.ts.dto;

import lombok.Data;

@Data
public class PrivateMessageInfo {
    private String title;
    private String description;
    private int receiver_id;
}
