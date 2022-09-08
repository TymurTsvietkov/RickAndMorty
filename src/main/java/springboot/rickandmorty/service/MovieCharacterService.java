package springboot.rickandmorty.service;

import java.util.List;
import springboot.rickandmorty.model.MovieCharacter;

public interface MovieCharacterService {
    void syncExternalCharacters();

    MovieCharacter getRandomCharacter();

    List<MovieCharacter> findAllByNameContains(String name);
}
