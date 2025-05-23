package org.egorr.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "person")
@Data
@NoArgsConstructor
@AllArgsConstructor
//Add validation
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int id;

    @Column(name = "person_name", length = 100, unique = true)
    @Pattern(regexp = "^[А-ЯЁA-Z][а-яёa-z]+ [А-ЯЁA-Z][а-яёa-z]+(?: [А-ЯЁA-Z][а-яёa-z]+)?$",
            message = "Enter your full name")
    private String name;

    @Column(name = "year_of_birth")
    @Min(value = 1920, message = "Year should be greater than 1920")
    @Max(value = 2020, message = "Year should be less than 2020")
    private int yearOfBirthday;

    @OneToMany(mappedBy = "owner", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Book> books;

    public Person(String name, int yearOfBirthday) {
        this.name = name;
        this.yearOfBirthday = yearOfBirthday;
    }

    public void addBook(Book book){
        books.add(book);
        book.setTakenAt(new Date());
        book.setOwner(this);
    }

    public void removeBook(Book book){
        books.remove(book);
        book.setOwner(null);
        book.setTakenAt(null);
    }
}
