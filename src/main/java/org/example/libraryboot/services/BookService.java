package org.example.libraryboot.services;

import org.example.libraryboot.models.Book;
import org.example.libraryboot.models.Person;
import org.example.libraryboot.repositories.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

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

    public Page<Book> findAll(int page, int bookPerPage, boolean sort) {
        Pageable pageable;
        if (sort) {
            pageable = PageRequest.of(page, bookPerPage, Sort.by("yearOfProduction"));
        }
        else {
            pageable = PageRequest.of(page, bookPerPage);
        }
        return booksRepository.findAll(pageable);
    }

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear) {
            return booksRepository.findAll(Sort.by("yearOfProduction"));
        }
        else {
            return booksRepository.findAll();
        }
    }

    public List<Book> findByPrefix(String prefix){
        return booksRepository.findByNameStartingWithIgnoreCase(prefix);
    }

    public Book findOne(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    public List<Person> getAllPeople() {
        return peopleRepository.findAll();
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setBookId(id);
        booksRepository.save(book);
    }


    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void assign(int bookId, int personId) {
        Book book = booksRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Not Found"));
        Person person = peopleRepository.findById(personId).orElseThrow(() -> new RuntimeException("Not Found"));
        Hibernate.initialize(person.getBooks());
        person.addBook(book);
        booksRepository.save(book);
    }


    @Transactional
    public void release(int bookId) {
        Book book = booksRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        int personId = book.getOwner().getId();

        System.out.println(personId);
        System.out.println(TransactionSynchronizationManager.isActualTransactionActive());

        Person person = peopleRepository.findById(personId).orElseThrow();
        Hibernate.initialize(person.getBooks());
        person.removeBook(book);
        booksRepository.save(book);
    }
}
