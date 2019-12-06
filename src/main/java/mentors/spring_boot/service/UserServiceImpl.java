package mentors.spring_boot.service;


import mentors.spring_boot.dao.UserDao;
import mentors.spring_boot.model.Role;
import org.springframework.stereotype.Service;

import mentors.spring_boot.model.User;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getById(long id) {

        return (User) userDao.selectById(id);
    }


    @Override
    public User getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }

    public List<User> getAll() {

        return userDao.selectAll();
    }


    public void add(User user, List<String> roles) {
        userDao.add(user, roles);
    }


    public long validate(String login, String password) {
        return userDao.validate(login, password);
    }


    public void update(User user, List<String> roles) {
        userDao.update(user, roles);
    }


    public void delete(long id) {
         userDao.delete(id);
    }

}
