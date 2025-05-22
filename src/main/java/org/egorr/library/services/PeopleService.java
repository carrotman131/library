package org.egorr.library.services;

import org.egorr.library.models.Book;
import org.egorr.library.models.Person;
import org.egorr.library.repositories.BooksRepository;
import org.egorr.library.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final BooksRepository booksRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BooksRepository booksRepository) {
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        return peopleRepository.findById(id).orElse(null);
    }

    public Optional<Person> findOneByName(String name) {
        return peopleRepository.findByName(name).stream().findAny();
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public List<Book> getAllBooksByPerson(int id) {
        List<Book> books = booksRepository.findByOwner(findOne(id));
        for (Book book : books) {
            if (book.getTakenAt() != null) {
                book.setExpired(checkExpired(book.getTakenAt(), 10));
            } else book.setExpired(true);
        }
        books.forEach(System.out::println);
        return books;
    }

    private static boolean checkExpired(Date date, long days) {
        long millisInDay = 24 * 60 * 60 * 1000;
        long diff = Math.abs(System.currentTimeMillis() - date.getTime());
        return diff >= days * millisInDay;
    }
}
