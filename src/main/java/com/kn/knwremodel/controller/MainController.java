package com.kn.knwremodel.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {
    @GetMapping(value="/")
    public String test(Model model) throws IOException{
        return "index";
    }
}