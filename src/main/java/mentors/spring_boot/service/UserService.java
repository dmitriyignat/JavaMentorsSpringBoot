package mentors.spring_boot.service;

import mentors.spring_boot.model.User;

import java.util.List;
public interface UserService {

        User getUserByLogin(String login);

        List<User> getAll();

        User getById(long id);

        long validate(String login);

        void add(User user);

        void delete(long id);

        void update(User user);

}
