package mentors.spring_boot.dao;

import mentors.spring_boot.model.Role;

import java.util.List;
import java.util.Set;

public interface RolesDao {

    List<Role> selectAll();
}
