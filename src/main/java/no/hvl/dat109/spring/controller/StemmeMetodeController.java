package no.hvl.dat109.spring.controller;

import no.hvl.dat109.spring.service.StemmeMetodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StemmeMetodeController {

    @Autowired
    private StemmeMetodeService stemmeMetodeService;

    @GetMapping("/stemmemetode")
    public String stemmeTest() {

        System.out.println(stemmeMetodeService.test());

        return "index";
    }
}
