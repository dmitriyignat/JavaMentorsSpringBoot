package mentors.spring_boot.service;

import mentors.spring_boot.dao.RolesDao;
import mentors.spring_boot.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RolesDao dao;

    @Override
    public List<Role> getAll() {

        return dao.selectAll();

    }
}
