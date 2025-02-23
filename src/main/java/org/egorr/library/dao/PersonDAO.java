package org.egorr.library.dao;

import org.egorr.library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAllPeople(){
        return jdbcTemplate.query("select * from person",
                new PersonMapper());
    }
    public Person getPerson(int id){
        return jdbcTemplate.query("select * from person where person_id = ?",
                new Object[]{id},
                new PersonMapper()).stream().findAny().orElse(null);
    }
    public void save(Person person) {
        jdbcTemplate.update("insert into person(person_name, year_of_birth) values(?, ?)",
                person.getName(), person.getYearOfBirthday());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("update person set person_name = ?, year_of_birth = ? where person_id = ?",
                person.getName(), person.getYearOfBirthday(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from person where person_id = ?", id);
    }
}
