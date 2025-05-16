package org.egorr.library.controllers;

import jakarta.validation.Valid;
import org.egorr.library.models.Book;
import org.egorr.library.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping
    public String index(Model model, @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                        @RequestParam(value = "book_per_page", required = false, defaultValue = "15") Integer bookPerPage,
                        @RequestParam(value = "sort_by_year", defaultValue = "false") boolean sortByYear) {
        List<Book> books;
        if (page == null || bookPerPage == null || page < 0 || bookPerPage <= 0) {
            books = bookService.findAll(sortByYear);
        } else {
            Page<Book> bookPage = bookService.findAll(page, bookPerPage, sortByYear);
            books = bookPage.getContent();
            model.addAttribute("bookPage", bookPage);
            model.addAttribute("book_per_page", bookPerPage);
        }

        model.addAttribute("books", books);
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
        Book book = bookService.findOne(id);
        model.addAttribute("book", book);
        return "books/edit";
    }


    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(System.out::println);
            return "books/new";
        }
        bookService.save(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(System.out::println);
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

    @GetMapping("/search")
    public String searchResult(Model model, @RequestParam(value = "prefix", required = false) String prefix) {
        if (prefix != null && !prefix.isEmpty()) {
            List<Book> books = bookService.findByPrefix(prefix);
            System.out.println(books);
            model.addAttribute("books", books);
        }
        return "books/search";
    }
}
