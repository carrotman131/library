package org.example.libraryboot.util;

import org.example.libraryboot.models.Person;
import org.example.libraryboot.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (peopleService.findOneByName(person.getName()).isPresent() && peopleService.findOneByName(person.getName()).get().getId() != person.getId()){
            errors.rejectValue("name", "", "This name is occupied");
        }
    }
}
