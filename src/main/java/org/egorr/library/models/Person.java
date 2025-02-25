package org.egorr.library.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//Add validation
public class Person {
    private int id;

    @Pattern(regexp = "^[А-ЯЁA-Z][а-яёa-z]+ [А-ЯЁA-Z][а-яёa-z]+(?: [А-ЯЁA-Z][а-яёa-z]+)?$",
            message = "Enter your full name")
    private String name;

    @Min(value = 1920, message = "Year should be greater than 1920")
    @Max(value = 2020, message = "Year should be less than 2020")
    private int yearOfBirthday;
}
