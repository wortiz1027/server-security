package co.edu.javeriana.servers.security.controller;

import co.edu.javeriana.servers.security.model.dtos.UserDto;
import co.edu.javeriana.servers.security.model.query.Response;
import co.edu.javeriana.servers.security.model.save.Status;
import co.edu.javeriana.servers.security.model.save.StatusCode;
import co.edu.javeriana.servers.security.service.UsersServicesDao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Map;

@RestController
@RequestMapping("security/api/v1")
@RequiredArgsConstructor
public class UserQueryController {

    private final UsersServicesDao service;

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> all(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page, size);
        Map<String, Object> response = service.getUsers(paging);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/users/details")
    public ResponseEntity<Response> user(@RequestParam(required = true) String username) {
        Response response = new Response();
        Status status = new Status();

        if (username.isEmpty()) {
            status.setCode(StatusCode.EMPTY.name());
            status.setDescription("There is an error, field username can't be empty or null");
            response.setStatus(status);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        UserDto user = service.getUserByUsername(username);

        if (user == null) {
            status.setCode(StatusCode.NO_EXIST.name());
            status.setDescription(String.format("User with %s does not exists!", username));
            response.setStatus(status);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        status.setCode(StatusCode.SUCCESS.name());
        status.setDescription(String.format("User with %s has information reported!", username));
        response.setStatus(status);
        response.setUser(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users/identifications")
    public ResponseEntity<Response> all(@RequestParam(required = true) BigInteger cedula) {
        Response response = new Response();
        Status status = new Status();

        if (cedula == null) {
            status.setCode(StatusCode.EMPTY.name());
            status.setDescription("There is an error, field username can't be empty or null");
            response.setStatus(status);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        UserDto user = service.getUserByIdentification(cedula);

        if (user == null) {
            status.setCode(StatusCode.NO_EXIST.name());
            status.setDescription(String.format("User with identification %s does not exists!", cedula));
            response.setStatus(status);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        status.setCode(StatusCode.SUCCESS.name());
        status.setDescription(String.format("User with identification %s has information reported!", cedula));
        response.setStatus(status);
        response.setUser(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
