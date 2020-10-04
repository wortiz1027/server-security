package co.edu.javeriana.servers.security.service;

import co.edu.javeriana.servers.security.model.Users;
import co.edu.javeriana.servers.security.model.save.Request;

public interface UsersServicesDao {

    boolean createUser(Request data);

    boolean isUserAvailable(String username);

    Users getUserByUsername(String username);

    Users update(Users u);

    void delete(Users u);

}
