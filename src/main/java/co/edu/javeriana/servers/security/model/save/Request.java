package co.edu.javeriana.servers.security.model.save;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Data
public class Request implements java.io.Serializable {

    private String codigo;
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
    private List<Roles> roles;

}
