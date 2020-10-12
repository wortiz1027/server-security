package co.edu.javeriana.servers.security.service;

import co.edu.javeriana.servers.security.model.Roles;

import java.util.List;

public interface RoleServicesDao {

    List<Roles> getAllRoles();

    Roles getInfoRole();

    void deleteRole();

    void updateRole();

}
