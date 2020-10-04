package co.edu.javeriana.servers.security.service;

import co.edu.javeriana.servers.security.model.Roles;
import co.edu.javeriana.servers.security.model.Users;
import co.edu.javeriana.servers.security.model.save.Request;
import co.edu.javeriana.servers.security.repository.UsersRepository;
import co.edu.javeriana.servers.security.util.Constants;
import co.edu.javeriana.servers.security.util.InfoLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.*;

@Service("customUserDetailsService")
public class CustomUserDetailServices implements UserDetailsService, UsersServicesDao {

    Logger logger = LoggerFactory.getLogger(CustomUserDetailServices.class);

    @Autowired
    private UsersRepository repository;

    @Autowired
    @Qualifier("customPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public boolean createUser(Request data) {
        try {
            Users user = new Users();
            List<Roles> roles = new ArrayList<>();

            buildUserRow(data, user, roles);

            repository.save(user);
        } catch (Exception e) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    private void buildUserRow(Request data, Users user, List<Roles> roles) {

        Date date = java.util.Date.from(data.getFechaNacimiento()
                                            .atStartOfDay()
                                            .atZone(ZoneId.systemDefault())
                                            .toInstant());

        data.getRoles().forEach(item -> {
            Roles rol = new Roles();
            rol.setIdRole(item.getIdRole());
            rol.setRole(item.getRole());
            roles.add(rol);
        });

        user.setIdUser(data.getCodigo());
        user.setCedula(data.getCedula());
        user.setNombre(data.getNombres());
        user.setApellido(data.getApellidos());
        user.setDireccion(data.getDireccion());
        user.setFechaNacimiento(date);
        user.setTelefono(data.getTelefono());
        user.setEmail(data.getEmail());
        user.setUsername(data.getUsername());
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        user.setEnable(data.getEnable());
        user.setAccountNonExpired(data.getAccountNonExpired());
        user.setCredentialNonExpired(data.getCredentialNonExpired());
        user.setAccountNonLocket(data.getAccountNonLocket());
        user.setRoles(roles);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUserAvailable(String username) {
        return repository.isUserAvailable(username) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public Users getUserByUsername(String username) {
        return repository.loadUserByUsername(username);
    }

    @Override
    @Transactional
    public Users update(Users u) {
        repository.saveAndFlush(u);
        return u;
    }

    @Override
    @Transactional
    public void delete(Users u) {
        //repository.delete(u.getIdUser());
    }

    @Override
    @Transactional(readOnly = true)
    @InfoLogger(origen = "loadUserByUsername")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repository.loadUserByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException(String.format(Constants.MSG_ERROR_USUARIO_NO_REGISTRADO, username));

        logger.debug("Usuario : " + username);
        return new User(user.getUsername(),
                        user.getPassword(),
                        Boolean.valueOf(user.getEnable()),
                        Boolean.valueOf(user.getAccountNonExpired()),
                        Boolean.valueOf(user.getCredentialNonExpired()),
                        Boolean.valueOf(user.getAccountNonLocket()),
                        getAuthorities(user.getRoles()));
    }

    @InfoLogger(origen = "getAuthorities")
    public Collection<? extends GrantedAuthority> getAuthorities(List<Roles> role) {
        List<GrantedAuthority> authoritiesList = new ArrayList<GrantedAuthority>(2);

        Iterator<Roles> iterRole = role.iterator();

        while (iterRole.hasNext()) {
            Roles rol = iterRole.next();
            authoritiesList.add(new SimpleGrantedAuthority(rol.getRole()));
        }

        return authoritiesList;
    }
}

