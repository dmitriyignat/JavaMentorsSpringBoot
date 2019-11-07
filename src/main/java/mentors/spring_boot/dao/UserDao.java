package mentors.spring_boot.dao;

import java.util.List;

public interface UserDao<T> {

    T getUserByLogin(String login);

    List<T> selectAll();

    T selectById(long id);

    long validate(String login, String password);

    void add(T t, String[] roles);

    void delete(long id);

    void update(T t, String[] roles);

}
