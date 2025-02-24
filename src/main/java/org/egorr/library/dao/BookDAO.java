package org.egorr.library.dao;

import org.egorr.library.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBooks() {
        return jdbcTemplate.query("select * from book", new BookMapper());
    }

    public Book getBook(int id) {
        return jdbcTemplate.query("select * from book where book_id = ?",
                new Object[]{id}, new BookMapper()).stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("insert into book(name, author, year_of_production) VALUES (?, ?, ?)",
                book.getName(), book.getAuthor(), book.getYearOfProduction());
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("update book set name = ?, author = ?, year_of_production = ? where book_id = ?",
                book.getName(), book.getAuthor(), book.getYearOfProduction(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from book where book_id = ?", id);
    }
    public void assign(int bookId, int personId){
        jdbcTemplate.update("update book set person_id = ? where book_id = ?", personId, bookId);
    }
    public void release(int bookId){
        jdbcTemplate.update("update book set person_id = null where book_id = ?", bookId);
    }
}
