package no.hvl.dat109.spring.service.Interfaces;

import no.hvl.dat109.spring.beans.ProsjektBean;

public interface IProsjektService {
   //TODO lag prosjekt interface

    ProsjektBean getProsjektById(int id);

    void addProsjekt(ProsjektBean prosjekt);
}
