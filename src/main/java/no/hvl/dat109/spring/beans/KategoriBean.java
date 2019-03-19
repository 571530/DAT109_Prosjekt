package no.hvl.dat109.spring.beans;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "kategori", schema = "prosjekt1")
public class KategoriBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int kategoriid;

    private String kategorinavn;

    @OneToMany(mappedBy = "prosjektkategori")
    private List<ProsjektBean> prosjekter;

    public KategoriBean() {
    }

    public KategoriBean(String kategorinavn) {
        this.kategorinavn = kategorinavn;
    }

    public int getKategoriid() {
        return kategoriid;
    }

    public void setKategoriid(int kategoriid) {
        this.kategoriid = kategoriid;
    }

    public String getKategorinavn() {
        return kategorinavn;
    }

    public void setKategorinavn(String kategorinavn) {
        this.kategorinavn = kategorinavn;
    }

    public List<ProsjektBean> getProsjekter() {
        return prosjekter;
    }

    public void setProsjekter(List<ProsjektBean> prosjekter) {
        this.prosjekter = prosjekter;
    }

    @Override
    public String toString() {
        return "KategoriBean{" +
                "kategoriid=" + kategoriid +
                ", kategorinavn='" + kategorinavn + '\'' +
                ", prosjekter=" + prosjekter +
                '}';
    }
}