package org.egorr.library.services;

import org.egorr.library.models.Book;
import org.egorr.library.models.Person;
import org.egorr.library.repositories.BooksRepository;
import org.egorr.library.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Book findOne(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    public List<Person> getAllPeople(){
        return peopleRepository.findAll();
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book){
        book.setBookId(id);
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    @Transactional
    public void assign(int bookId, int personId) {
        Book book = booksRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Not Found"));
        Person person = peopleRepository.findById(personId).orElseThrow(() -> new RuntimeException("Not Found"));
        book.setOwner(person);
        booksRepository.save(book);
    }


    @Transactional
    public void release(int bookId){
        Book book = booksRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setOwner(null);
        booksRepository.save(book);
    }
}
