package co.edu.javeriana.servers.security.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_sec_roles", schema = "securitydb")
public class Roles implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID_ROLE")
    private Long idRole;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    private String role;

    @ManyToMany(mappedBy = "roles")
    private List<Users> userList;

}