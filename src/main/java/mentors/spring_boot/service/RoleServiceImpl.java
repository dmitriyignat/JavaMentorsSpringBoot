package mentors.spring_boot.service;

import mentors.spring_boot.repositories.RolesRepository;
import mentors.spring_boot.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    private final RolesRepository rolesRepository;

    public RoleServiceImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public List<Role> getAll() {
        return rolesRepository.findAll();

    }
}
