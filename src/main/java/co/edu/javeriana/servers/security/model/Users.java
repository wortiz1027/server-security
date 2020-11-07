package co.edu.javeriana.servers.security.model;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_sec_users", schema = "securitydb")
public class Users implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "ID_USER")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idUser;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CEDULA")
    private BigInteger cedula;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRES")
    private String nombre;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "APELLIDOS")
    private String apellido;

    @Size(max = 100)
    @Column(name = "DIRECCION")
    private String direccion;

    @Column(name = "FECHA_NACIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;

    @Size(max = 30)
    @Column(name = "TELEFONO")
    private String telefono;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "EMAIL")
    private String email;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "username")
    private String username;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PASSWORD")
    private String password;

    @Size(max = 10)
    @Column(name = "ENABLE")
    private String enable;

    @Size(max = 10)
    @Column(name = "ACCOUNT_NON_EXPIRED")
    private String accountNonExpired;

    @Size(max = 10)
    @Column(name = "CREDENTIAL_NON_EXPIRED")
    private String credentialNonExpired;

    @Size(max = 10)
    @Column(name = "ACCOUNT_NON_LOCKET")
    private String accountNonLocket;

    @JoinTable(name = "tbl_sec_user_roles", joinColumns = {
            @JoinColumn(name = "USER_ID", referencedColumnName = "ID_USER")}, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID_ROLE")})
    @ManyToMany(cascade=CascadeType.MERGE)
    private List<Roles> roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_type", updatable = true, nullable = false)
    private Types types;

}