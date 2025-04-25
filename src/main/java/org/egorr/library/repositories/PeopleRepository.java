package org.egorr.library.repositories;

import jakarta.validation.constraints.Pattern;
import org.egorr.library.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    List<Person> findByName(@Pattern(regexp = "^[А-ЯЁA-Z][а-яёa-z]+ [А-ЯЁA-Z][а-яёa-z]+(?: [А-ЯЁA-Z][а-яёa-z]+)?$",
            message = "Enter your full name") String name);
}
