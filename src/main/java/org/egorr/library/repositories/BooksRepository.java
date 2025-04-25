package org.egorr.library.repositories;

import org.egorr.library.models.Book;
import org.egorr.library.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByOwner(Person owner);
}
