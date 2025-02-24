package org.egorr.library.controllers;

import org.egorr.library.dao.BookDAO;
import org.egorr.library.dao.PersonDAO;
import org.egorr.library.models.Book;
import org.egorr.library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }
    @GetMapping
    public String getAllBooks(Model model){
        model.addAttribute("books", bookDAO.getAllBooks());
        return "books/index";
    }
    @GetMapping("/{id}")
    public String getBook(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.getBook(id);
        model.addAttribute("book", book);
        model.addAttribute("people", personDAO.getAllPeople());
        if (book.getPersonId() != 0) model.addAttribute("owner", personDAO.getPerson(book.getPersonId()));
        return "books/show";
    }
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.getBook(id));
        return "books/edit";
    }
    // Add validation here
    @PostMapping
    public String create(@ModelAttribute("book") Book book) {
        bookDAO.save(book);
        return "redirect:/books";
    }
    // Add validation here
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") Book book, @PathVariable("id") int id) {
        bookDAO.update(id, book);
        return "redirect:/books";
    }
    // Add a validation here
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }
    @PatchMapping("/assign")
    public String assignBook(@ModelAttribute("book") Book book){
        System.out.println(book.getBookId());
        System.out.println(book.getPersonId());
        bookDAO.assign(book.getBookId(), book.getPersonId());
        return "redirect:/books/" + book.getBookId();
    }

    @PatchMapping("/release")
    public String releaseBook(@ModelAttribute("book") Book book){
        System.out.println(book.getBookId());
        System.out.println(book.getPersonId());
        bookDAO.release(book.getBookId());
        return "redirect:/books/" + book.getBookId();
    }
}
