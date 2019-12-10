package mentors.spring_boot.dao;

import mentors.spring_boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import mentors.spring_boot.model.Role;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Repository
public class UserDaoHibernateImpl implements UserDao {

    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public User getUserByLogin(String login) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u from User u WHERE u.login = :login", User.class);
        query.setParameter("login", login);
        return (User)query.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List selectAll() {
        return (List<User>)entityManager.createQuery("SELECT u FROM User u").getResultList();
    }

    public User selectById(long id) {
        return (User)entityManager.find(User.class, id);
    }

    @SuppressWarnings("unchecked")
    public long validate(String login, String password) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(u) FROM User u WHERE u.login = :login", Long.class);
        query.setParameter("login", login);
        return query.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public void add(User user, List<String> roles) {
        for (String role : roles) {
            TypedQuery<Role> query =  entityManager.createQuery("SELECT r from Role r WHERE r.name = :name", Role.class);
            query.setParameter("name", role);
            user.addRole(query.getSingleResult());
        }
        entityManager.persist(user);
    }

    public void add(User user) {
        entityManager.persist(user);
    }

    public void delete(long id) {
        entityManager.remove((User)selectById(id));
    }

    @SuppressWarnings("unchecked")
    public void update(User userNew, List<String> roles) {
        User userOld = (User)selectById(userNew.getId());
        Set<Role> newRoles = new HashSet<>();

        for (String role : roles) {
            TypedQuery<Role> query =  entityManager.createQuery("SELECT r from Role r WHERE r.name = :name", Role.class);
            query.setParameter("name", role);
            newRoles.add(query.getSingleResult());
        }

        userOld.setLogin(userNew.getLogin());
        userOld.setName(userNew.getName());
        userOld.setPassword(userNew.getPassword());
        userOld.setRoles(newRoles);
        entityManager.merge(userOld);
    }

    public void update(User userNew) {
        User userOld = (User)selectById(userNew.getId());
        userOld.setLogin(userNew.getLogin());
        userOld.setName(userNew.getName());
        userOld.setPassword(userNew.getPassword());
        userOld.setRoles(userNew.getRoles());
        entityManager.merge(userOld);

    }
}
