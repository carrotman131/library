package org.egorr.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Entity
@Table(name = "book")
@Data
@ToString(exclude = "owner")
@NoArgsConstructor
@AllArgsConstructor
//Add validation
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;

    @Column(name = "name", nullable = false, length = 100)
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String name;

    @Column(name = "author", nullable = false, length = 100)
    @Pattern(regexp = "^[А-ЯЁA-Z][а-яёa-z]+ [А-ЯЁA-Z][а-яёa-z]+(?: [А-ЯЁA-Z][а-яёa-z]+)?$",
            message = "Enter the name of author")
    private String author;

    @Column(name = "year_of_production", nullable = false, length = 100)
    @Min(value = 1, message = "Year should be greater than 1")
    private int yearOfProduction;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date takenAt;

    @Transient
    private boolean isExpired;


    public Book(Person owner, String name, String author, int yearOfProduction) {
        this.owner = owner;
        this.name = name;
        this.author = author;
        this.yearOfProduction = yearOfProduction;
    }
}
