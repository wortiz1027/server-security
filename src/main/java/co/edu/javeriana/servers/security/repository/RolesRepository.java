package co.edu.javeriana.servers.security.repository;

import co.edu.javeriana.servers.security.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {

    @Query("SELECT r FROM Roles r")
    List<Roles> getRoleListNq();

}
