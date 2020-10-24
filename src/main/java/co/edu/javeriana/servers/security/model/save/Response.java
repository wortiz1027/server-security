package co.edu.javeriana.servers.security.model.save;

import co.edu.javeriana.servers.security.model.dtos.UserDto;
import lombok.Data;

@Data
public class Response implements java.io.Serializable {

    private Status status;
    private UserDto u;

}
