package co.edu.javeriana.servers.security.model.save;

import lombok.Data;

@Data
public class Status implements java.io.Serializable {

    private String code;
    private String description;

}
