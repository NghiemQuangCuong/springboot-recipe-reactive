package com.cuongnghiem.springbootrecipe.model;

import lombok.*;

/**
 * Created by cuongnghiem on 27/09/2021
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnitOfMeasure {
    private String id;
    private String description;
}
