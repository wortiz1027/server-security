package co.edu.javeriana.servers.security.model.save;

import lombok.Data;

@Data
public class Response implements java.io.Serializable {

    private Status status;
    private Request user;

}
