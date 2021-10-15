package com.cuongnghiem.springbootrecipe.command;

import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCommand {
    private Long id;
    private String description;
}
