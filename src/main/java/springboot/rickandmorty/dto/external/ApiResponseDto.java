package springboot.rickandmorty.dto.external;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiResponseDto {
    private ApiInfoDto info;
    private ApiCharacterDto[] results;
}
