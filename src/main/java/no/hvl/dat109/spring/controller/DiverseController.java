package no.hvl.dat109.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class DiverseController {

    @PostMapping("/registrer_deg")
    String registerBruker(@RequestParam String redirect_url, @RequestParam String epost, HttpSession session) {
        session.setAttribute("epost", epost);

        return "redirect:" + redirect_url;
    }

    @GetMapping("/registrer_deg")
    String getRegistrerBruker(@RequestParam String redirect_url, Model model) {
        model.addAttribute("redirect_url", redirect_url);
        return "registrer_deg";
    }

}

