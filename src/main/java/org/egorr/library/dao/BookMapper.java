package org.egorr.library.dao;

import org.egorr.library.models.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        int bookId = rs.getInt("book_id");
        int personId = rs.getInt("person_id");
        String name = rs.getString("name");
        String author = rs.getString("author");
        int yearOfProduction = rs.getInt("year_of_production");
        return new Book(bookId, personId, name, author, yearOfProduction);
    }
}
