package org.twt.ts.dto;

import lombok.Data;

import java.util.List;

@Data
public class MessageInfo {
    private String title;
    private String description;

    private List<Integer> disabled_id;
}
