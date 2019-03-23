package no.hvl.dat109.spring.service;

import no.hvl.dat109.spring.beans.UserGroupBean;
import no.hvl.dat109.spring.beans.UsersBean;
import no.hvl.dat109.spring.repository.UsersRepository;
import no.hvl.dat109.spring.service.Interfaces.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

import static no.hvl.dat109.prosjekt.utilities.Utilities.checkPassword;
import static no.hvl.dat109.prosjekt.utilities.Utilities.hashPassword;

@Service
public class UsersService implements IUsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserGroupService userGroupService;

    @Override
    public String test() {
        usersRepository.findAll().forEach(System.out::println);
        return "";
    }


    @Override
    public UsersBean createNewUser(String username, String password) {
        UsersBean user = new UsersBean(username, hashPassword(password), userGroupService.getUsergroupById(2));
        usersRepository.save(user);
        return getUserByName(username);
    }

    @Override
    public UsersBean getUserByName(String username) {
        Iterator<UsersBean> users = usersRepository.findAll().iterator();

        UsersBean user;
        while (users.hasNext()) {
            user = users.next();
            if (user.getUsername().equals(username) && !user.isExpired()) return user;
        }

        return null;
    }

    @Override
    public UsersBean validUser(String username, String password) {
        UsersBean user = getUserByName(username);
        if (user == null || !checkPassword(password, user)) return null;
        return user;
    }

    @Override
    public void createVoterUser(String username) {
        //Create a user with voter usergroup
        UserGroupBean group = userGroupService.getUsergroupById(3);
        usersRepository.save(new UsersBean(username, group));
    }

    @Override
    public void removeUser(UsersBean user) {
        usersRepository.delete(user);
    }

    @Override
    public UsersBean getUserById(int id) {
        return usersRepository.findById(id).orElse(null);
    }
}
