package co.edu.javeriana.servers.security.service;

import co.edu.javeriana.servers.security.model.Roles;
import co.edu.javeriana.servers.security.model.Types;
import co.edu.javeriana.servers.security.model.Users;
import co.edu.javeriana.servers.security.model.dtos.RolesDto;
import co.edu.javeriana.servers.security.model.dtos.TypesDto;
import co.edu.javeriana.servers.security.model.dtos.UserDto;
import co.edu.javeriana.servers.security.model.save.Request;
import co.edu.javeriana.servers.security.repository.UsersRepository;
import co.edu.javeriana.servers.security.util.Constants;
import co.edu.javeriana.servers.security.util.InfoLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
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

        Types type = new Types();
        type.setType(data.getTypes().getType());
        type.setCode(data.getTypes().getCode());
        type.setDescription(data.getTypes().getDescription());

        user.setTypes(type);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUserAvailable(String username) {
        return repository.isUserAvailable(username) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getUsers(Pageable paging) {
        Page<Users> pages = this.repository.findAll(paging);
        List<UserDto> users = new ArrayList<>();

        for (Users row : pages.getContent()) {
            UserDto item = new UserDto();
            item.setIdUser(row.getIdUser());
            item.setCedula(row.getCedula());
            item.setNombre(row.getNombre());
            item.setApellido(row.getApellido());
            item.setDireccion(row.getDireccion());
            item.setFechaNacimiento(row.getFechaNacimiento());
            item.setTelefono(row.getTelefono());
            item.setEmail(row.getEmail());
            item.setUsername(row.getUsername());
            item.setEnable(row.getEnable());
            item.setAccountNonExpired(row.getAccountNonExpired());
            item.setCredentialNonExpired(row.getCredentialNonExpired());
            item.setAccountNonLocket(row.getAccountNonLocket());

            TypesDto type = new TypesDto();
            type.setType(row.getTypes().getType());
            type.setCode(row.getTypes().getCode());
            type.setDescription(row.getTypes().getDescription());

            item.setTypes(type);
            List<RolesDto> roles = new ArrayList<>();

            for (Roles r : row.getRoles()) {
                RolesDto rol = new RolesDto();
                rol.setIdRole(r.getIdRole());
                rol.setRole(r.getRole());
                roles.add(rol);
            }

            item.setRoles(roles);
            users.add(item);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        response.put("currentPage", pages.getNumber());
        response.put("totalItems", pages.getTotalElements());
        response.put("totalPages", pages.getTotalPages());

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByUsername(String username) {
        try {
            Users row = repository.loadUserByUsername(username);

            UserDto user = new UserDto();
            user.setIdUser(row.getIdUser());
            user.setCedula(row.getCedula());
            user.setNombre(row.getNombre());
            user.setApellido(row.getApellido());
            user.setDireccion(row.getDireccion());
            user.setFechaNacimiento(row.getFechaNacimiento());
            user.setTelefono(row.getTelefono());
            user.setEmail(row.getEmail());
            user.setUsername(row.getUsername());
            user.setEnable(row.getEnable());
            user.setAccountNonExpired(row.getAccountNonExpired());
            user.setCredentialNonExpired(row.getCredentialNonExpired());
            user.setAccountNonLocket(row.getAccountNonLocket());

            TypesDto type = new TypesDto();
            type.setType(row.getTypes().getType());
            type.setCode(row.getTypes().getCode());
            type.setDescription(row.getTypes().getDescription());

            user.setTypes(type);
            List<RolesDto> roles = new ArrayList<>();

            for (Roles r : row.getRoles()) {
                RolesDto rol = new RolesDto();
                rol.setIdRole(r.getIdRole());
                rol.setRole(r.getRole());
                roles.add(rol);
            }

            user.setRoles(roles);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByIdentification(BigInteger cedula) {
        try {
            Users row = repository.loadUserByIdentification(cedula);

            logger.debug("NOMBRE_CONSULTA >> {}", row.getNombre());

            UserDto user = new UserDto();
            user.setIdUser(row.getIdUser());
            user.setCedula(row.getCedula());
            user.setNombre(row.getNombre());
            user.setApellido(row.getApellido());
            user.setDireccion(row.getDireccion());
            user.setFechaNacimiento(row.getFechaNacimiento());
            user.setTelefono(row.getTelefono());
            user.setEmail(row.getEmail());
            user.setUsername(row.getUsername());
            user.setEnable(row.getEnable());
            user.setAccountNonExpired(row.getAccountNonExpired());
            user.setCredentialNonExpired(row.getCredentialNonExpired());
            user.setAccountNonLocket(row.getAccountNonLocket());

            TypesDto type = new TypesDto();
            type.setType(row.getTypes().getType());
            type.setCode(row.getTypes().getCode());
            type.setDescription(row.getTypes().getDescription());

            user.setTypes(type);
            List<RolesDto> roles = new ArrayList<>();

            for (Roles r : row.getRoles()) {
                RolesDto rol = new RolesDto();
                rol.setIdRole(r.getIdRole());
                rol.setRole(r.getRole());
                roles.add(rol);
            }

            user.setRoles(roles);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public Request update(Request data) {
        Date date = java.util.Date.from(data.getFechaNacimiento()
                .atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());

        Users user = new Users();

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

        List<Roles> roles = new ArrayList<>();

        for (RolesDto r : data.getRoles()) {
            Roles rol = new Roles();
            rol.setIdRole(r.getIdRole());
            rol.setRole(r.getRole());
            roles.add(rol);
        }

        user.setRoles(roles);

        Types type = new Types();
        type.setType(data.getTypes().getType());
        type.setCode(data.getTypes().getCode());
        type.setDescription(data.getTypes().getDescription());

        user.setTypes(type);

        repository.saveAndFlush(user);
        return data;
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

