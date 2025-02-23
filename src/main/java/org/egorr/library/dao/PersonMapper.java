package org.egorr.library.dao;

import org.egorr.library.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("person_id");
        String name = rs.getString("person_name");
        int year = rs.getInt("year_of_birth");
        return new Person(id, name, year);
    }
}
