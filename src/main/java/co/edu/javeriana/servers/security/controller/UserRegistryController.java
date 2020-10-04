package co.edu.javeriana.servers.security.controller;

import co.edu.javeriana.servers.security.model.save.Request;
import co.edu.javeriana.servers.security.model.save.Response;
import co.edu.javeriana.servers.security.service.UsersServicesDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("security/api/v1")
@RequiredArgsConstructor
public class UserRegistryController {

    private final UsersServicesDao service;

    @PostMapping("/users")
    public ResponseEntity<Response> save(@RequestBody(required = true) Request data) {
        Response response = new Response();

        if (this.service.isUserAvailable(data.getUsername())) {
            response.setDescriptions(String.format("User %s exists!", data.getUsername()));
            return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
        }

        boolean isSuccess = service.createUser(data);

        if (!isSuccess) {
            response.setDescriptions(String.format("There is an error creating user %s%s", data.getNombres(), data.getApellidos()));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.setDescriptions(String.format("User %s%s has been created", data.getNombres(), data.getApellidos()));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
