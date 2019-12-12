package mentors.spring_boot.service;

import mentors.spring_boot.repositories.UserRepository;
import org.springframework.stereotype.Service;

import mentors.spring_boot.model.User;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User getById(long id) {
        return repository.getOne(id);
    }

    @Override
    public User getUserByLogin(String login) {
        return repository.findByLogin(login);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public void add(User user) {
        repository.save(user);
    }

    @Override
    public long validate(String login) {
        return repository.countUsersByLogin(login);
    }

    @Override
    public void update(User user) {

        repository.save(user);
    }

    @Override
    public void delete(long id) {
        repository.delete(repository.findById(id).get());
    }

}
