package co.edu.javeriana.servers.security.service;

import co.edu.javeriana.servers.security.model.Roles;
import co.edu.javeriana.servers.security.model.Users;

import java.util.List;

public interface UsersServicesDao {

    void createUser(Users u, List<Roles> role);

    boolean isUserAvailable(String username);

    Users getUserByUsername(String username);

    Users update(Users u);

    void delete(Users u);

}
