package co.edu.javeriana.servers.security.controller;

import co.edu.javeriana.servers.security.model.save.Request;
import co.edu.javeriana.servers.security.model.save.Response;
import co.edu.javeriana.servers.security.model.save.Status;
import co.edu.javeriana.servers.security.model.save.StatusCode;
import co.edu.javeriana.servers.security.service.UsersServicesDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("registry/api/v1")
@RequiredArgsConstructor
public class UserRegistryController {

    private final UsersServicesDao service;

    @PostMapping("/users")
    public ResponseEntity<Response> save(@RequestBody(required = true) Request data) {
        Response response = new Response();
        Status status = new Status();

        if (this.service.isUserAvailable(data.getUsername())) {
            status.setCode(StatusCode.NO_EXIST.name());
            status.setDescription(String.format("User %s exists!", data.getUsername()));
            response.setStatus(status);
            return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
        }

        boolean isSuccess = service.createUser(data);

        if (!isSuccess) {
            status.setCode(StatusCode.NO_EXIST.name());
            status.setDescription(String.format("There is an error creating user %s%s!", data.getUsername()));
            response.setStatus(status);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        status.setCode(StatusCode.SUCCESS.name());
        status.setDescription(String.format("User %s has been created!", data.getUsername()));
        response.setStatus(status);
        response.setUser(data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
