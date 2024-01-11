package com.example.dustdetector.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicPagesController {
    @GetMapping("medische-informatie")
    public String medischeInformatie() {
        return "medische-informatie";
    }

    @GetMapping("support")
    public String support() {
        return "support";
    }    

    @GetMapping("onze-organisatie")
    public String onzeOrganisatie() {
        return "onze-organisatie";
    }
}
