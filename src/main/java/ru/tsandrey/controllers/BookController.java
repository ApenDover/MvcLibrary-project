package ru.tsandrey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.tsandrey.DAO.BookDAO;
import ru.tsandrey.DAO.BookRotationDAO;
import ru.tsandrey.DAO.PersonDAO;
import ru.tsandrey.model.Book;
import ru.tsandrey.model.Person;
import ru.tsandrey.model.SearchParams;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class BookController {

    private BookDAO bookDAO;
    private PersonDAO personDAO;
    private BookRotationDAO bookRotationDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO, BookRotationDAO bookRotationDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookRotationDAO = bookRotationDAO;
    }

    @GetMapping("/books")
    public String books(@ModelAttribute("searchParams") SearchParams searchParams, Model model) {

        model.addAttribute("allBooks", bookDAO.allBooks());

        if (Objects.equals(searchParams.getSearchString(), "")) {
            model.addAttribute("allBooks", bookDAO.allBooks());
        }
        if (!Objects.equals(searchParams.getSearchString(), null)) {
            model.addAttribute("allBooks", bookDAO.findBooks(searchParams));
        }
        return "book/books";
    }

    @GetMapping("/books/new")
    public String newBook(@ModelAttribute("book") Book book, Model model) {
        model.addAttribute("authorList", bookDAO.getAuthorList());
        return "book/new";
    }

    @GetMapping("/books/new/{authorId}")
    public String newBook(@PathVariable("authorId") int authorId, @ModelAttribute("book") Book book, Model model) {
        model.addAttribute("authorList", bookDAO.getAuthorListById(authorId));
        return "book/new";
    }

    @PostMapping("/books/{authorId}")
    public String createWithAuthor(@PathVariable("authorId") int authorId, Model model, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authorList", bookDAO.getAuthorListById(authorId));
            return "book/new";
        }
        bookDAO.create(book);
        return "redirect:/authors/" + authorId + "/open";
    }

    @PostMapping("/books")
    public String create(Model model, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authorList", bookDAO.getAuthorList());
            return "book/new";
        }
        bookDAO.create(book);
        return "redirect:/books";
    }

    @GetMapping("/books/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("authorName", bookDAO.getAuthor(id));
        model.addAttribute("authorList", bookDAO.getAuthorList());
        model.addAttribute("editBook", bookDAO.getBook(id));
        return "book/edit";
    }

    @PatchMapping("/books/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authorName", bookDAO.getAuthor(id));
            model.addAttribute("authorList", bookDAO.getAuthorList());
            model.addAttribute("editBook", bookDAO.getBook(id));
            return "book/edit";
        }
        bookDAO.update(book, id);
        return "redirect:/books";
    }

    @PatchMapping("/books/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        bookDAO.remove(id);
        return "redirect:/books";
    }

    @GetMapping("/books/{id}/open")
    public String openBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.joinBookById(id));
        model.addAttribute("people", personDAO.peopleList());
        model.addAttribute("nowHaveRotationList", bookRotationDAO.bookRotationNowByIdBook(id));
        model.addAttribute("howManyTake", bookRotationDAO.howManyTake(id));
        model.addAttribute("whoWasLast", bookRotationDAO.whoWasLast(id));
        return "/book/open";
    }

    @PostMapping("/books/{id}/open/addperson")
    public String takeBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookRotationDAO.giveBook(id, person.getId());
        return "redirect:/books/" + id + "/open";
    }

}
