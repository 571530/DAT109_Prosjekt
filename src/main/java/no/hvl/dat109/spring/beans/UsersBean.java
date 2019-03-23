package no.hvl.dat109.spring.beans;

import no.hvl.dat109.prosjekt.utilities.Utilities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users", schema = "prosjekt1")
public class UsersBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userid;

    private String username, password;

    private boolean expired;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usergroup")
    private UserGroupBean userGroup;

    @OneToMany(mappedBy = "prosjektEiger", fetch = FetchType.LAZY)
    private List<ProsjektBean> prosjekter;

    public UsersBean() {
    }

    public UsersBean(String username, UserGroupBean groupBean) {
        this.username = username;
        this.password = "NO-PASSWORD";
        this.userGroup = groupBean;
    }

    public UsersBean(String username, String password, UserGroupBean userGroup) {
        this.username = username;
        this.password = password;
        this.userGroup = userGroup;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserGroupBean getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroupBean userGroup) {
        this.userGroup = userGroup;
    }

    public List<ProsjektBean> getProsjekter() {
        return prosjekter;
    }

    public void setProsjekter(List<ProsjektBean> prosjekter) {
        this.prosjekter = prosjekter;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public int getUsergroupLevel() {
        return userGroup.getGrouplevel();
    }

    @Override
    public String toString() {
        return "UsersBean{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", expired=" + expired +
                ", usergroup=" + userGroup.getGrouplevel() +
                '}';
    }
}
