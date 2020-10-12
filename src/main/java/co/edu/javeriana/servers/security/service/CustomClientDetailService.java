package co.edu.javeriana.servers.security.service;

import java.util.*;

import co.edu.javeriana.servers.security.model.OauthClientDetails;
import co.edu.javeriana.servers.security.repository.OAuthClientRepository;
import co.edu.javeriana.servers.security.util.Constants;
import co.edu.javeriana.servers.security.util.InfoLogger;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service("customClientDetailsService")
public class CustomClientDetailService implements ClientDetailsService, ClientServicesDao {

    Logger logger = LoggerFactory.getLogger(CustomClientDetailService.class);

    private final OAuthClientRepository repository;

    @Override
    @Transactional(readOnly = true)
    @InfoLogger(origen = "loadClientByClientId")
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        if (repository.isClientAvailable(clientId) == 0) {
            throw new ClientRegistrationException(String.format(Constants.MSG_ERROR_CLIENTE_NO_REGISTRADO, clientId));
        }

        OauthClientDetails client = repository.loadClientById(clientId);

        BaseClientDetails clientDetails = new BaseClientDetails();

        List<String> authorities = Arrays.asList(client.getAuthorizedGrantTypes().split(","));

        List<GrantedAuthority> authoritiesList = new ArrayList<GrantedAuthority>();

        for (String s : authorities) {
            authoritiesList.add(new SimpleGrantedAuthority(s));
        }

        Set<String> uris = new HashSet<String>(Arrays.asList(client.getWebServerRedirectUri().split(",")));

        for (String s : uris) {
            logger.info("URIS :: " + s);
        }

        logger.debug("CLIENT_SECRET :: " + client.getClientSecret());

        clientDetails.setClientId(client.getClientId());
        clientDetails.setScope(Arrays.asList(client.getScope().split(",")));
        clientDetails.setAuthorizedGrantTypes(Arrays.asList(client.getAuthorizedGrantTypes().split(",")));
        clientDetails.setAuthorities(authoritiesList);
        clientDetails.setAccessTokenValiditySeconds(client.getAccessTokenValidity().intValue());
        clientDetails.setClientSecret(client.getClientSecret());
        clientDetails.setRegisteredRedirectUri(uris);
        clientDetails.setResourceIds(Arrays.asList(client.getResourceId().split(",")));

        String approve = client.getAutoapprove() == null ? "false" : "true";

        if (approve.equalsIgnoreCase("true"))
            clientDetails.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(client.getAutoapprove()));
        else
            clientDetails.setAutoApproveScopes(new HashSet<String>());

        return clientDetails;
    }

    @Override
    @Transactional(readOnly = false)
    public void createClient(OauthClientDetails client) {
        if (client != null) {
            repository.save(client);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUserAvailable(String clientId) {
        if (clientId != null) {
            repository.isClientAvailable(clientId);
        }

        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public OauthClientDetails getClientById(String clientId) {

        OauthClientDetails client = null;

        if (!clientId.equalsIgnoreCase("")) {
            client = repository.loadClientById(clientId);
        }

        return client;
    }

    @Override
    @Transactional(readOnly = false)
    public OauthClientDetails update(OauthClientDetails client) {
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(OauthClientDetails client) {

    }

}
