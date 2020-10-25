package co.edu.javeriana.servers.security.service;

import co.edu.javeriana.servers.security.model.Users;
import co.edu.javeriana.servers.security.model.dtos.UserDto;
import co.edu.javeriana.servers.security.model.save.Request;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.Map;

public interface UsersServicesDao {

    boolean createUser(Request data);
    boolean isUserAvailable(String username);
    Map<String, Object> getUsers(Pageable paging);
    UserDto getUserByUsername(String username);
    UserDto getUserByIdentification(BigInteger cedula);
    Request update(Request data);
    void delete(Users u);

}
