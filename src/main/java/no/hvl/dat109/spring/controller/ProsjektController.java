package no.hvl.dat109.spring.controller;

import no.hvl.dat109.spring.beans.BedriftBean;
import no.hvl.dat109.spring.beans.ProsjektBean;
import no.hvl.dat109.spring.service.Interfaces.IBedriftService;
import no.hvl.dat109.spring.service.Interfaces.IProsjektService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProsjektController {

    //Todo make useful controller methods

    @Autowired
    private IProsjektService prosjektService;

    @Autowired
    private IBedriftService bedriftService;

    @GetMapping("/prosjekter")
    String getAlleProsjekter(Model model) {
        model.addAttribute("prosjekter", prosjektService.getAlleProsjekter());

        return "prosjekter.html";
    }

    @GetMapping("/prosjekt/{id}")
    String getProsjektById(@PathVariable("id") int id, Model model) {

        ProsjektBean prosjekt = prosjektService.getProsjektById(id);

        if (prosjekt == null) {
            return "error";
        }
        System.out.println(prosjekt.getSammarbeidsbedrift() + " THIS IS BEDRIFT BOYYY");

        model.addAttribute("samarbeidspartner", prosjekt.getSammarbeidsbedrift());
        model.addAttribute("prosjekt", prosjekt);

        return "prosjekt";

    }

    @GetMapping("/prosjekt/add")
    String addProsjekt(Model model) {
        model.addAttribute("bedrifter", bedriftService.getAlleBedrifter());
        return "registrer_prosjekt";
    }

    @PostMapping("/prosjekt/add")
    String addProsjektPostRequest(
            @RequestParam String prosjektnavn,
            @RequestParam String prosjektbeskrivelse,
            @RequestParam int samarbeidspartner) {
        //  System.out.println(id);

        //Finn en bedrift fra id-en til comboboxen
        BedriftBean bedrift = bedriftService.getBedriftById(samarbeidspartner);

        ProsjektBean prosjekt = new ProsjektBean(prosjektnavn, prosjektbeskrivelse, bedrift, "");

        prosjektService.addProsjekt(prosjekt);

        return "redirect:/prosjekt/" + prosjekt.getProsjektid();
    }
}
