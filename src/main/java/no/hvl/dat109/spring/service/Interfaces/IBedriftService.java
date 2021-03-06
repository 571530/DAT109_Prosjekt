package no.hvl.dat109.spring.service.Interfaces;

import no.hvl.dat109.spring.beans.BedriftBean;

public interface IBedriftService {

    //TODO
    Iterable<BedriftBean> getAlleBedrifter();

    BedriftBean getBedriftById(int id);

    void addBedrift(BedriftBean bedrift);

    void removeBedrift(BedriftBean bedrift);

    void updateBedriftNavn(BedriftBean bedrift, String nyttNavn);

    boolean exists(String name);

    boolean exists(int id);

}
