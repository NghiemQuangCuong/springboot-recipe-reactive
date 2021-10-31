package com.cuongnghiem.springbootrecipe.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by cuongnghiem on 27/09/2021
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class UnitOfMeasure {
    @Id
    private String id;
    private String description;
}
