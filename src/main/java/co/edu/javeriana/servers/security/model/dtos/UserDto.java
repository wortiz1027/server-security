package co.edu.javeriana.servers.security.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements java.io.Serializable {

    private Long idUser;

    private BigInteger cedula;
    private String nombre;
    private String apellido;
    private String direccion;
    private Date fechaNacimiento;
    private String telefono;
    private String email;
    private String username;
    private String password;
    private String enable;
    private String accountNonExpired;
    private String credentialNonExpired;
    private String accountNonLocket;
    private List<RolesDto> roles;
    private TypesDto types;

}
