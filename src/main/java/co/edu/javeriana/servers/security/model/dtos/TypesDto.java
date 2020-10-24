package co.edu.javeriana.servers.security.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypesDto implements java.io.Serializable {

    private Long type;
    private String code;
    private String description;

}
