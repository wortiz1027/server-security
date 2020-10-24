package co.edu.javeriana.servers.security.model.query;

import co.edu.javeriana.servers.security.model.dtos.UserDto;
import co.edu.javeriana.servers.security.model.save.Status;
import lombok.Data;

@Data
public class Response implements java.io.Serializable {

    private Status status;
    private UserDto user;

}
