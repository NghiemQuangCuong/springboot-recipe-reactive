package com.cuongnghiem.springbootrecipe.command;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnitOfMeasureCommand {
    private String id;
    private String description;
}
