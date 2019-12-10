package mentors.spring_boot.dao;

import mentors.spring_boot.model.Role;
import mentors.spring_boot.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {

    User getUserByLogin(String login);

    List<User> selectAll();

    User selectById(long id);

    long validate(String login, String password);

    void add(User user, List<String> roles);

    void add(User user);

    void delete(long id);

    void update(User user, List<String> roles);

    void update(User user);

}
