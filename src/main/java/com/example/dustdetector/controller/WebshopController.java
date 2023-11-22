package com.example.dustdetector.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebshopController {

    @GetMapping(value = { "/webshop" })
    public String home() {
        return "webshop";
    }
}
