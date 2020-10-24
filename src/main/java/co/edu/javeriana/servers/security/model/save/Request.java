package co.edu.javeriana.servers.security.model.save;

import co.edu.javeriana.servers.security.model.dtos.RolesDto;
import co.edu.javeriana.servers.security.model.dtos.TypesDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Request implements java.io.Serializable {

    private long codigo;
    private BigInteger cedula;
    private String nombres;
    private String apellidos;
    private String direccion;
    private LocalDate fechaNacimiento;
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
