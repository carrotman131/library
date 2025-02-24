package org.egorr.library.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//Add validation
public class Book {

    private int bookId;

    private int personId;

    private String name;

    private String author;

    private int yearOfProduction;
}
