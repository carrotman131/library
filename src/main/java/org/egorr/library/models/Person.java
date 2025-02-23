package org.egorr.library.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//Add validation
public class Person {
    private int id;

    private String name;

    private int yearOfBirthday;
}
