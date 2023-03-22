package org.twt.ts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MessageInfo {
    private String title;
    private String description;

    private List<Integer> disabled_id;
}
