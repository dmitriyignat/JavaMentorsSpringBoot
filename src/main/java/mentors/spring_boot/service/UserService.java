package mentors.spring_boot.service;

import mentors.spring_boot.model.Role;
import mentors.spring_boot.model.User;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Set;

public interface UserService {
//        @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
        User getUserByLogin(String login);

//        @PreAuthorize("hasRole('ROLE_ADMIN')")
        List<User> getAll();


        User getById(long id);


        long validate(String login, String password);


        void add(User user, List<String> roles);


        void delete(long id);


        void update(User user, List<String> roles);

}
