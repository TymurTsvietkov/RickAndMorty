package springboot.rickandmorty.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springboot.rickandmorty.dto.CharacterResponseDto;
import springboot.rickandmorty.dto.mapper.MovieCharacterMapper;
import springboot.rickandmorty.model.MovieCharacter;
import springboot.rickandmorty.service.MovieCharacterService;

@RestController
@RequestMapping("/movie-characters")
@AllArgsConstructor
public class MovieCharacterController {
    private final MovieCharacterService movieCharacterService;
    private final MovieCharacterMapper mapper;

    @GetMapping("/random")
    @ApiOperation("Get a random character")
    public CharacterResponseDto getRandom() {
        MovieCharacter character = movieCharacterService.getRandomCharacter();
        return mapper.toResponseDto(character);
    }

    @GetMapping("/by-name")
    @ApiOperation("Get all characters that contain in name `name`")
    public List<CharacterResponseDto> findAllByName(@RequestParam("nameParam") String name) {
        return movieCharacterService.findAllByNameContains(name)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
