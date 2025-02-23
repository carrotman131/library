package org.egorr.library.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//Add validation
public class Book {
    private int personId;

    private int bookId;

    private String name;

    private String author;

    private int yearOfProduction;
}
