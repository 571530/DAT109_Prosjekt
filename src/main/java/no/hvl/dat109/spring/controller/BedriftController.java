package no.hvl.dat109.spring.controller;

import no.hvl.dat109.prosjekt.Processing;
import no.hvl.dat109.spring.beans.BedriftBean;
import no.hvl.dat109.spring.service.Interfaces.IBedriftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;

@Controller
public class BedriftController {

    //Todo make useful controller methods

    @Autowired
    private IBedriftService repository;

    @GetMapping("/bedrifter")
    public String getAllBedrifter(Model model) {
        model.addAttribute("test", repository.getAll());
        return "index";
    }

    @GetMapping("/bedrift/{id}")
    public String getBedriftById(@PathVariable("id") int id, Model model) {
        BedriftBean bean = repository.getBedriftById(id);
        Processing processing = new Processing(bean);
        model.addAttribute("test", bean.toString());
        return "index";
    }

    @GetMapping("/bedrift/add/{navn}/{beskrivelse}")
    public String addBedrift(@PathVariable("navn") String navn, @PathVariable("beskrivelse") String beskrivelse, Model model) {

        //If for some reason database is empty again, you can easily add new one for debugging

        if (repository.exists(navn)) return "redirect:/bedrifter";
        repository.addBedrift(navn, beskrivelse);
        model.addAttribute("test", repository.getAll());

        //example of redirect
        return "redirect:/bedrifter";
    }


}