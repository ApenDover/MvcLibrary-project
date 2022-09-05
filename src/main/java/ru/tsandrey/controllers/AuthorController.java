package ru.tsandrey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.tsandrey.DAO.AuthorDAO;
import ru.tsandrey.model.Author;
import ru.tsandrey.model.SearchParams;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class AuthorController {

    private AuthorDAO authorDAO;

    @Autowired

    public AuthorController(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }


    @GetMapping("/authors")
    public String books(Model model, @ModelAttribute("searchParams")SearchParams searchParams)
    {

        model.addAttribute("authors", authorDAO.authorList());

        if (Objects.equals(searchParams.getSearchString(), ""))
        {
            model.addAttribute("authors", authorDAO.authorList());
        }
        if (!Objects.equals(searchParams.getSearchString(), null)) {
            model.addAttribute("authors", authorDAO.findAuthor(searchParams));
        }
        return "author/authors";
    }

    @GetMapping("/authors/new")
    public String newAuthor(@ModelAttribute("author") Author author)
    {
        return "/author/new";
    }

    @PostMapping("/authors")
    public String create(@ModelAttribute("author") @Valid Author author, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            return "author/new";
        }
        authorDAO.save(author);
        return "redirect:/authors";
    }

    @GetMapping("/authors/{id}/edit")
    public String editAuthor(@PathVariable("id") int id, Model model)
    {
        model.addAttribute("author", authorDAO.getAuthor(id));
        return "author/edit";
    }

    @PatchMapping("/authors/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("author") @Valid Author author, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            return "author/edit";
        }
        authorDAO.update(id, author);
        return "redirect:/authors";
    }

    @PatchMapping("/authors/{id}/delete")
    public String delete(@PathVariable("id") int id)
    {
        authorDAO.delete(id);
        return "redirect:/authors";
    }

    @GetMapping("/authors/{id}/open")
    public String showAuthor(@PathVariable("id") int id, Model model)
    {
        model.addAttribute("author", authorDAO.getAuthor(id));
        model.addAttribute("bookList", authorDAO.bookList(id));
        return "author/open";
    }

}
