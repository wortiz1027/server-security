package co.edu.javeriana.servers.security.service;

import co.edu.javeriana.servers.security.model.OauthClientDetails;

public interface ClientServicesDao {

    void createClient(OauthClientDetails client);

    boolean isUserAvailable(String clientId);

    OauthClientDetails getClientById(String clientId);

    OauthClientDetails update(OauthClientDetails client);

    void delete(OauthClientDetails client);

}
