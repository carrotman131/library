package org.example.libraryboot.repositories;

import org.example.libraryboot.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByOwner(Person owner);
    List<Book> findByNameStartingWithIgnoreCase(String prefix);
}
