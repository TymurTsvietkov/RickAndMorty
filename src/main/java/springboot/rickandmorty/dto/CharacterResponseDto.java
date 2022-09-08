package springboot.rickandmorty.dto;

import lombok.Getter;
import lombok.Setter;
import springboot.rickandmorty.model.Gender;
import springboot.rickandmorty.model.Status;

@Setter
@Getter
public class CharacterResponseDto {
    private Long id;
    private Long externalId;
    private String name;
    private Status status;
    private Gender gender;
    private String species;
    private String type;
}
