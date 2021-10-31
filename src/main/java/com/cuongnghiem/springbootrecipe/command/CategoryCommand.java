package com.cuongnghiem.springbootrecipe.command;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryCommand {
    private String id;
    private String description;
}
