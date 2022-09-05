package ru.tsandrey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.tsandrey.DAO.BookRotationDAO;
import ru.tsandrey.DAO.PersonDAO;
import ru.tsandrey.model.Person;
import ru.tsandrey.model.SearchParams;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class PeopleController {

    private PersonDAO personDAO;
    private BookRotationDAO bookRotationDAO;
//    private final PersonValidator personValidator;


    @Autowired
    public PeopleController(PersonDAO personDAO, BookRotationDAO bookRotationDAO) {
        this.personDAO = personDAO;
        this.bookRotationDAO = bookRotationDAO;
//        this.personValidator = personValidator;
    }


    @GetMapping("/people")
    public String people(Model model, @ModelAttribute("searchParams")SearchParams searchParams)
    {
        model.addAttribute("people", personDAO.peopleList());

        if (Objects.equals(searchParams.getSearchString(), ""))
        {
            model.addAttribute("people", personDAO.peopleList());
        }
        if (!Objects.equals(searchParams.getSearchString(), null)) {
            model.addAttribute("people", personDAO.findPerson(searchParams));
        }


        return "people/people";
    }

    @GetMapping("/people/new")
    public String newPerson(@ModelAttribute("person") Person person)
    {
        return "/people/new";
    }

    @PostMapping("/people")
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
            return "people/new";
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/people/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model)
    {
        model.addAttribute("person", personDAO.getPerson(id));
        return "people/edit";
    }

    @PatchMapping("/people/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personDAO.update(id, person);
        return "redirect:/people";

    }

    @PatchMapping("/people/{id}/delete")
    public String delete(@PathVariable("id") int id)
    {
        personDAO.delete(id);
        return "redirect:/people";
    }

    @GetMapping("/people/{id}/open")
    public String open(@PathVariable("id") int id, Model model)
    {
        model.addAttribute("person", personDAO.getPerson(id));
        model.addAttribute("rotationList", bookRotationDAO.bookRotationByIdPerson(id));
        model.addAttribute("nowHaveRotationList", bookRotationDAO.bookRotationNowByIdPerson(id));
        return "people/open";
    }

    @GetMapping("/people/{id}/backBook/{book}")
    public String backBook(@PathVariable("id") int idperson, @PathVariable("book") int idbook)
    {
        bookRotationDAO.backBook(idperson, idbook);
        return "redirect:/people/" + idperson +"/open";
    }

}
