package springboot.rickandmorty.dto.mapper;

import org.springframework.stereotype.Component;
import springboot.rickandmorty.dto.CharacterResponseDto;
import springboot.rickandmorty.dto.external.ApiCharacterDto;
import springboot.rickandmorty.model.Gender;
import springboot.rickandmorty.model.MovieCharacter;
import springboot.rickandmorty.model.Status;

@Component
public class MovieCharacterMapper {

    public MovieCharacter toModel(ApiCharacterDto dto) {
        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setName(dto.getName());
        movieCharacter.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
        movieCharacter.setStatus(Status.valueOf(dto.getStatus().toUpperCase()));
        movieCharacter.setExternalId(dto.getId());
        return movieCharacter;
    }

    public CharacterResponseDto toResponseDto(MovieCharacter movieCharacter) {
        CharacterResponseDto dto = new CharacterResponseDto();
        dto.setId(movieCharacter.getId());
        dto.setExternalId(movieCharacter.getExternalId());
        dto.setName(movieCharacter.getName());
        dto.setStatus(movieCharacter.getStatus());
        dto.setGender(movieCharacter.getGender());
        return dto;
    }

    public MovieCharacter updateCharacterToModel(MovieCharacter existing, ApiCharacterDto dto) {
        existing.setName(dto.getName());
        existing.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
        existing.setStatus(Status.valueOf(dto.getStatus().toUpperCase()));
        return existing;
    }
}
