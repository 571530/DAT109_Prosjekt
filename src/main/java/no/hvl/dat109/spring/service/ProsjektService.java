package no.hvl.dat109.spring.service;

import no.hvl.dat109.prosjekt.handlers.FileHandler;
import no.hvl.dat109.spring.beans.ArrangementBean;
import no.hvl.dat109.spring.beans.ProsjektBean;
import no.hvl.dat109.spring.beans.UsersBean;
import no.hvl.dat109.spring.repository.ProsjektRepository;
import no.hvl.dat109.spring.service.Interfaces.IProsjektService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;

@Service
public class ProsjektService implements IProsjektService {
    //TODO implement prosjektservice

    @Autowired
    private ProsjektRepository prosjektRepository;

    @Autowired
    private ArrangementdeltagelseService deltagelseService;

    @Override
    public ProsjektBean getProsjektById(int id) {
        return prosjektRepository.findById(id).orElse(null);
    }

    @Override
    public void addProsjekt(ProsjektBean prosjekt) {
        prosjektRepository.save(prosjekt);
    }

    @Override
    public boolean exists(String name) {
        for (ProsjektBean prosjektBean : prosjektRepository.findAll()) {
            if (prosjektBean.getProsjektnavn().equals(name)) return true;
        }
        return false;
    }

    public ProsjektBean getProsjektByName(String name) {
        for (ProsjektBean prosjektBean : prosjektRepository.findAll()) {
            if (prosjektBean.getProsjektnavn().equals(name)) return prosjektBean;
        }
        return null;
    }

    @Override
    public void updateProsjekt(ProsjektBean prosjekt) {
        prosjektRepository.save(prosjekt);
    }

    @Override
    public void removeAllProjects() {
        prosjektRepository.deleteAll();
    }

    @Override
    public void removeProject(ProsjektBean prosjekt) {
        deltagelseService.removeProsjektFromDeltagelse(prosjekt);
        prosjektRepository.delete(prosjekt);
    }

    @Override
    public ProsjektBean getProsjektFromOwner(UsersBean user) {
        Iterator<ProsjektBean> alleProsjekter = getAlleProsjekter().iterator();

        ProsjektBean prosjekt;
        while (alleProsjekter.hasNext()) {
            prosjekt = alleProsjekter.next();
            if (prosjekt.erEigerAvProsjekt(user)) return prosjekt;
        }
        return null;
    }

    @Override
    public void changeNameOfProject(ProsjektBean prosjekt, String newName) {
        prosjekt.setProsjektnavn(newName);
        prosjektRepository.save(prosjekt);
    }

    @Override
    public void changeBeskrivelse(ProsjektBean prosjekt, String beskrivelse) {
        prosjekt.setProsjektbeskrivelse(beskrivelse);
        prosjektRepository.save(prosjekt);
    }

    @Override
    public void updatePicturePath(ProsjektBean prosjekt, String path, ArrangementBean arrangement) {
        ProsjektBean prosjektBean = prosjektRepository.findById(prosjekt.getProsjektid()).orElse(null);
        if (prosjektBean == null) return;
        prosjektBean.setPictureurl(path);
        prosjektRepository.save(prosjektBean);
    }

    @Override
    public void updateBackgroundPath(ProsjektBean prosjekt, String path, ArrangementBean arrangement) {
        ProsjektBean bean = prosjektRepository.findById(prosjekt.getProsjektid()).orElse(null);
        if (bean == null) return;
        bean.setBackgroundurl(path);
        prosjektRepository.save(bean);
    }

    @Override
    public String createLogo(MultipartFile logo, ProsjektBean prosjekt, ArrangementBean arrangement) {
        return FileHandler.createLogoImage(logo, prosjekt, arrangement).replace("src/main/resources/static/", "");
    }

    @Override
    public String createBackground(MultipartFile background, ProsjektBean prosjekt, ArrangementBean arrangment) {
        return FileHandler.createBackgroundImage(background, prosjekt, arrangment).replace("src/main/resources/static/", "");
    }

    @Override
    public Iterable<ProsjektBean> getAlleProsjekter() {
        return prosjektRepository.findAll();
    }
}