package co.edu.javeriana.servers.security.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_user_type", schema = "securitydb")
public class Types {

    @Id
    @NotNull
    @Column(name = "type_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long type;

    @Column(name = "type_code")
    private String code;

    @Column(name = "type_desc")
    private String description;

    @OneToMany(mappedBy = "types", fetch = FetchType.LAZY)
    private List<Users> users;

}
