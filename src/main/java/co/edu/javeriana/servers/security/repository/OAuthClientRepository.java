package co.edu.javeriana.servers.security.repository;

import co.edu.javeriana.servers.security.model.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthClientRepository extends JpaRepository<OauthClientDetails, Integer> {

    @Query("SELECT count(c) FROM OauthClientDetails c WHERE c.clientId = :ipClientId")
    int isClientAvailable(@Param("ipClientId") String clientId);

    @Query("SELECT client FROM OauthClientDetails client WHERE client.clientId = :ipClientId")
    OauthClientDetails loadClientById(@Param("ipClientId") String clientId);

}
