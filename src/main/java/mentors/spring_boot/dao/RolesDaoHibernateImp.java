package mentors.spring_boot.dao;


import mentors.spring_boot.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Repository
public class RolesDaoHibernateImp implements RolesDao {

    @Autowired
    EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<Role> selectAll() {
        return entityManager.createQuery("SELECT r from Role r").getResultList();
    }
}
