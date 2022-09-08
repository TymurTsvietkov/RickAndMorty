package springboot.rickandmorty.dto.external;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiCharacterDto {
    private Long id;
    private String name;
    private String status;
    private String gender;
    private String species;
    private String type;
}
