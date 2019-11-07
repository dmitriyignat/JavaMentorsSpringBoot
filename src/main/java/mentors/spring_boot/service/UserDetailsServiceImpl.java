package mentors.spring_boot.service;

import mentors.spring_boot.dao.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mentors.spring_boot.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDao dao;

    public UserDetailsServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = (User)dao.getUserByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(), "{noop}" + user.getPassword(), user.getRoles());

    }
}
