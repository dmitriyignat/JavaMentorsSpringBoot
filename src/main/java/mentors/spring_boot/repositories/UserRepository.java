package mentors.spring_boot.repositories;

import mentors.spring_boot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select user from User user where user.login = :login")
    User findByLogin(String login);

    @Query("select count(user) from User user where user.login = :login and user.password = :password")
    Long findUsersByLoginAndPassword(String login, String password);
}
