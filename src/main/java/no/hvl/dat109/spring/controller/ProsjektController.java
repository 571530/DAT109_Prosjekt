package no.hvl.dat109.spring.controller;

import no.hvl.dat109.prosjekt.utilities.EmailUtil;
import no.hvl.dat109.prosjekt.handlers.FileHandler;
import no.hvl.dat109.prosjekt.utilities.UrlPaths;
import no.hvl.dat109.prosjekt.utilities.Utilities;
import no.hvl.dat109.spring.beans.*;
import no.hvl.dat109.spring.service.Interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static no.hvl.dat109.prosjekt.handlers.FileHandler.removeProjectQrCode;
import static no.hvl.dat109.prosjekt.handlers.Processing.*;

@Controller
public class ProsjektController {

    //Todo make useful controller methods

    @Autowired
    private IProsjektService prosjektService;

    @Autowired
    private IArrangementService arrangementService;

    @Autowired
    private IBedriftService bedriftService;

    @Autowired
    private IKategoriService kategoriService;

    @Autowired
    private IStudieService studieService;

    @Autowired
    private IUsersService userService;

    @GetMapping("/prosjekter")
    String getAlleProsjekter(Model model) {
        model.addAttribute("prosjekter", prosjektService.getAlleProsjekter());

        return "adminpages/prosjekter.html";
    }

    @GetMapping(UrlPaths.SHOW_QR)
    String getProsjektQR(@PathVariable("id") int id, Model model) {

        ProsjektBean prosjekt = prosjektService.getProsjektById(id);

        if (prosjekt == null) {
            return UrlPaths.ERRORPAGE;
        }

        model.addAttribute("prosjekt", prosjekt);

        return UrlPaths.STAND_QR_HTML;
    }

    @GetMapping(UrlPaths.CREATE_QR_IMAGE)
    String createProsjektQR(@PathVariable("id") int id) {

        ProsjektBean prosjekt = prosjektService.getProsjektById(id);

        if (prosjekt == null) {
            return UrlPaths.ERRORPAGE;
        }

        //Denne koden kjører av en eller annen merkelig grunn når jeg faktisk bare skriver linken inn i nettleser #spooky
        removeProjectQrCode(prosjekt);
        setQrLink(prosjekt);

        // OBS! serveren kan redirecte før qrkoden bildet er lagret og vil ikke være oppdattert uten er refresh
        return "redirect:/prosjekt/" + id + "/qr";
    }

    @GetMapping(UrlPaths.PROJECT_WITH_ID)
    String getProsjektById(@PathVariable("id") int id, Model model, HttpSession session) {

        if (session.getAttribute("epost") == null) {
            return "redirect:/registrer_deg?redirect_url=" + "/prosjekt/" + id;
        }

        ProsjektBean prosjekt = prosjektService.getProsjektById(id);
        UsersBean user = (UsersBean) session.getAttribute("user");

        if (prosjekt == null) {
            return UrlPaths.ERRORPAGE;
        }

        // Guess who's back, back again
        // Bedrift boyy is back, tell a friend
        //System.out.println(prosjekt.getSammarbeidsbedrift() + " THIS IS BEDRIFT BOYYY🔥");

        model.addAttribute("samarbeidspartner", prosjekt.getSammarbeidsbedrift());
        model.addAttribute("prosjekt", prosjekt);

        return UrlPaths.STAND_HTML;
    }

    @GetMapping(UrlPaths.ADD_PROSJEKT)
    String addProsjekt(Model model) {
        model.addAttribute("kategorier", kategoriService.getAllKategorier());
        model.addAttribute("bedrifter", bedriftService.getAlleBedrifter());
        return UrlPaths.REGISTRER_PROSJEKT_HTML;
    }

    @PostMapping(UrlPaths.ADD_PROSJEKT)
    String addProsjektPostRequest(
            @RequestParam String prosjektnavn,
            @RequestParam String prosjektbeskrivelse,
            @RequestParam int samarbeidspartner,
            @RequestParam int institutt,
            @RequestParam String email,
            HttpSession session) {
        //  System.out.println(id);

        //Finn en bedrift fra id-en til comboboxen
        BedriftBean bedrift = bedriftService.getBedriftById(samarbeidspartner);

        //Finn studie fra box
        StudieBean studie = studieService.getStudieById(institutt);

        //Lag en user med emailen vi fikk
        String password = Utilities.generateShortPassword(5);
        UsersBean user = userService.createNewUser(email, password);

        //Lag prosjektet med alt vi har fått så langt
        ProsjektBean prosjekt = new ProsjektBean(prosjektnavn, prosjektbeskrivelse, bedrift, studie, user);
        prosjektService.addProsjekt(prosjekt);

        //Send email with password
        sendEmail(email, user, password);

        //Etter prosjektet er laget kan kan vi danne qr bilde link
        setQrLink(prosjekt);

        session.setAttribute("epost", email);
        session.setAttribute("user", user);

        return "redirect:/prosjekt/" + prosjekt.getProsjektid();
    }

    @PostMapping("/prosjekt/{id}/remove")
    String removeProject(@PathVariable("id") int id, HttpSession session) {
        //Finn prosjektet
        ProsjektBean prosjekt = prosjektService.getProsjektById(id);
        //Slett alle prosjektfiler
        FileHandler.removeProject(prosjekt);

        //Fjern prosjektet og fjern useren fra databasen
        prosjektService.removeProject(prosjekt);
        userService.removeUser(prosjekt.getProsjektEiger());

        //Fjern session attributter
        session.removeAttribute("user");
        session.removeAttribute("epost");
        return "redirect:/index";
    }

    @GetMapping("/prosjekter/apocalypse")
    String removeAllProsjektFiles() {
        FileHandler.removeAllProjects();
        return "index";
    }

    /**
     * Send email with login info for STAND_HTML user
     *
     * @param email email of user
     * @param user  user with username and password
     */
    private void sendEmail(String email, UsersBean user, String password) {
        String messagebody = String.format("Username: %s\nPassword: %s", user.getUsername(), password);
        Thread thread = new Thread(() -> EmailUtil.sendEmail(email, messagebody));
        thread.start();
    }

    /**
     * Sets the QR link to the database
     *
     * @param prosjekt prosjekt to set the qr link to
     */
    private void setQrLink(ProsjektBean prosjekt) {
        prosjekt.setShortenedurl(generateShortlink(prosjekt));
        prosjekt.setQrimagepath(getRelativeProjectQRCode(prosjekt));
        prosjektService.updateProsjekt(prosjekt);
    }
}