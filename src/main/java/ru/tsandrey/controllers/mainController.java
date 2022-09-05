package ru.tsandrey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mainController {

    @GetMapping("/")
    public String index()
    {
        return "/index";
    }

    @GetMapping("/admin")
    public String admin()
    {
        return "/admin";
    }
}
