package co.edu.javeriana.servers.security.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RolesDto implements java.io.Serializable {
    private Long idRole;
    private String role;
}
