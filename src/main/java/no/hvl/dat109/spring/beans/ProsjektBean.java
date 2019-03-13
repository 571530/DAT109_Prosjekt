package no.hvl.dat109.spring.beans;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "prosjekt", schema = "prosjekt1")
public class ProsjektBean implements Serializable {

    //Todo can benefit from new toString, otherwise done

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prosjektid;
    private String prosjektnavn;
    private String prosjektbeskrivelse;
    private String qrcodeurl;

    public ProsjektBean() {
    }

    public ProsjektBean(int prosjektid, String prosjektnavn, String prosjektbeskrivelse, String qrcodeurl) {
        this.prosjektid = prosjektid;
        this.prosjektnavn = prosjektnavn;
        this.prosjektbeskrivelse = prosjektbeskrivelse;
        this.qrcodeurl = qrcodeurl;
    }

    public ProsjektBean(String prosjektnavn, String prosjektbeskrivelse, String qrcodeurl) {
        this.prosjektnavn = prosjektnavn;
        this.prosjektbeskrivelse = prosjektbeskrivelse;
        this.qrcodeurl = qrcodeurl;
    }


    @Column(name = "prosjektid")
    public int getProsjektid() {
        return prosjektid;
    }

    public void setProsjektid(int prosjektid) {
        this.prosjektid = prosjektid;
    }

    @Basic
    @Column(name = "prosjektnavn")
    public String getProsjektnavn() {
        return prosjektnavn;
    }

    public void setProsjektnavn(String prosjektnavn) {
        this.prosjektnavn = prosjektnavn;
    }

    @Basic
    @Column(name = "prosjektbeskrivelse")
    public String getProsjektbeskrivelse() {
        return prosjektbeskrivelse;
    }

    public void setProsjektbeskrivelse(String prosjektbeskrivelse) {
        this.prosjektbeskrivelse = prosjektbeskrivelse;
    }

    @Basic
    @Column(name = "qrcodeurl")
    public String getQrcodeurl() {
        return qrcodeurl;
    }

    public void setQrcodeurl(String qrcodeurl) {
        this.qrcodeurl = qrcodeurl;
    }

    @Override
    public String toString() {
        return "ProsjektBean{" +
                "prosjektid=" + prosjektid +
                ", prosjektnavn='" + prosjektnavn + '\'' +
                ", prosjektbeskrivelse='" + prosjektbeskrivelse + '\'' +
                ", qrcodeurl='" + qrcodeurl + '\'' +
                '}';
    }
}
