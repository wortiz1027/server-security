package co.edu.javeriana.servers.security.repository;

import co.edu.javeriana.servers.security.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    @Query("SELECT count(u) FROM Users u WHERE u.username = :ipUsername")
    int isUserAvailable(@Param("ipUsername") String ipUsername);

    @Query("SELECT u FROM Users u WHERE u.username = :ipUsername")
    Users loadUserByUsername(@Param("ipUsername") String username);

    @Query("SELECT u FROM Users u WHERE u.cedula = :ipCedula")
    Users loadUserByIdentification(@Param("ipCedula") BigInteger cedula);

    Page<Users> findAll(Pageable pageable);
}
