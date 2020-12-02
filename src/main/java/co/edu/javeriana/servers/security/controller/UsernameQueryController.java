package co.edu.javeriana.servers.security.controller;

import co.edu.javeriana.servers.security.model.dtos.UserDto;
import co.edu.javeriana.servers.security.model.query.Response;
import co.edu.javeriana.servers.security.model.save.Status;
import co.edu.javeriana.servers.security.model.save.StatusCode;
import co.edu.javeriana.servers.security.service.UsersServicesDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("queries/api/v1")
@RequiredArgsConstructor
public class UsernameQueryController {

    private final UsersServicesDao service;

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

}
