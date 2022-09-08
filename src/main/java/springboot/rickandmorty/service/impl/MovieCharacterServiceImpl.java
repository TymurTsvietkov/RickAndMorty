package springboot.rickandmorty.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import springboot.rickandmorty.dto.external.ApiCharacterDto;
import springboot.rickandmorty.dto.external.ApiResponseDto;
import springboot.rickandmorty.dto.mapper.MovieCharacterMapper;
import springboot.rickandmorty.model.MovieCharacter;
import springboot.rickandmorty.repository.MovieCharacterRepository;
import springboot.rickandmorty.service.HttpClient;
import springboot.rickandmorty.service.MovieCharacterService;

@Log4j2
@Service
@RequiredArgsConstructor
public class MovieCharacterServiceImpl implements MovieCharacterService {
    private final HttpClient httpClient;
    private final MovieCharacterRepository movieCharacterRepository;
    private final MovieCharacterMapper movieCharacterMapper;
    @Value("${client.link}")
    private String clientUrl;

    @PostConstruct
    @Scheduled(cron = "0 8 * * * ?", zone = "Europe/Kiev")
    @Override
    public void syncExternalCharacters() {
        log.info("syncExternalCharacters was called at " + LocalDateTime.now());
        ApiResponseDto apiResponseDto = httpClient.get(clientUrl, ApiResponseDto.class);
        saveDtosToDb(apiResponseDto);
        while (apiResponseDto.getInfo().getNext() != null) {
            apiResponseDto = httpClient.get(apiResponseDto.getInfo().getNext(),
                    ApiResponseDto.class);
            saveDtosToDb(apiResponseDto);
        }
    }

    @Override
    public MovieCharacter getRandomCharacter() {
        long count = movieCharacterRepository.count();
        long randomId = (long) (Math.random() * count);
        return movieCharacterRepository.findById(randomId).orElseThrow(
                () -> new NoSuchElementException("Can't find character by id: " + randomId));
    }

    @Override
    public List<MovieCharacter> findAllByNameContains(String name) {
        return movieCharacterRepository.findAllByNameContains(name);
    }

    List<MovieCharacter> saveDtosToDb(ApiResponseDto apiResponseDto) {
        Map<Long, ApiCharacterDto> externalDtos = Arrays
                .stream(apiResponseDto.getResults())
                .collect(Collectors.toMap(ApiCharacterDto::getId, Function.identity()));
        Set<Long> externalIds = externalDtos.keySet();
        List<MovieCharacter> existingCharacters =
                movieCharacterRepository.findAllByExternalIdIn(externalIds);
        Map<Long, MovieCharacter> existingCharactersWithIds = existingCharacters
                .stream()
                .collect(Collectors.toMap(MovieCharacter::getExternalId, Function.identity()));
        Set<Long> existingIds = existingCharactersWithIds.keySet();
        externalIds.retainAll(existingIds);
        List<MovieCharacter> charactersToUpdate = existingCharacters
                .stream()
                .map(ch -> movieCharacterMapper
                        .updateCharacterToModel(ch, externalDtos.get(ch.getExternalId())))
                .collect(Collectors.toList());
        existingIds = existingCharactersWithIds.keySet();
        externalIds.removeAll(existingIds);
        List<MovieCharacter> charactersToSave = externalIds
                .stream()
                .map(i -> movieCharacterMapper.toModel(externalDtos.get(i)))
                .collect(Collectors.toList());
        charactersToSave.addAll(charactersToUpdate);
        return charactersToSave;
    }
}
