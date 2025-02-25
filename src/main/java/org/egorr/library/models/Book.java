package org.egorr.library.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String name;
    @Pattern(regexp = "^[А-ЯЁA-Z][а-яёa-z]+ [А-ЯЁA-Z][а-яёa-z]+(?: [А-ЯЁA-Z][а-яёa-z]+)?$",
            message = "Enter the name of author")
    private String author;
    @Min(value = 1, message = "Year should be greater than 1")
    private int yearOfProduction;
}
