package org.egorr.library.controllers;

import jakarta.validation.Valid;
import org.egorr.library.models.Book;
import org.egorr.library.models.Person;
import org.egorr.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable("id") int id, Model model) {
        Book book = bookService.findOne(id);
        model.addAttribute("book", book);
        model.addAttribute("people", bookService.getAllPeople());
        if (book.getOwner() != null) model.addAttribute("owner", book.getOwner());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }

    // Add validation here
    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        bookService.save(book);
        return "redirect:/books";
    }

    // Add validation here
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()){
            return "books/edit";
        }
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/assign")
    public String assignBook(@RequestParam("bookId") int bookId, @RequestParam("personId") int personId) {
        bookService.assign(bookId, personId);
        return "redirect:/books/" + bookId;
    }

    @PatchMapping("/release")
    public String releaseBook(@RequestParam("bookId") int bookId) {
        bookService.release(bookId);
        return "redirect:/books/" + bookId;
    }
}
