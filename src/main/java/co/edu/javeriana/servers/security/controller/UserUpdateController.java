package co.edu.javeriana.servers.security.controller;

import co.edu.javeriana.servers.security.model.Users;
import co.edu.javeriana.servers.security.model.dtos.UserDto;
import co.edu.javeriana.servers.security.model.save.Request;
import co.edu.javeriana.servers.security.model.save.Response;
import co.edu.javeriana.servers.security.model.save.Status;
import co.edu.javeriana.servers.security.model.save.StatusCode;
import co.edu.javeriana.servers.security.service.UsersServicesDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("security/api/v1")
@RequiredArgsConstructor
public class UserUpdateController {

    private final UsersServicesDao service;

    @PutMapping("/users")
    public ResponseEntity<Response> update(@RequestBody(required = true) Request data) {
        Response response = new Response();
        Status status = new Status();

        if (!this.service.isUserAvailable(data.getUsername())) {
            status.setCode(StatusCode.NO_EXIST.name());
            status.setDescription(String.format("User %s does not exists!", data.getUsername()));
            response.setStatus(status);
            return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
        }

        Request u = service.update(data);

        if (u == null) {
            status.setCode(StatusCode.ERROR.name());
            status.setDescription(String.format("There is an error updating user %s%s", data.getNombres(), data.getApellidos()));
            response.setStatus(status);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        status.setCode(StatusCode.UPDATED.name());
        status.setDescription(String.format("User %s%s has been updated", data.getNombres(), data.getApellidos()));
        response.setStatus(status);
        response.setUser(u);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
