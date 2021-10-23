package com.yago.Alkemy.service;

import com.yago.Alkemy.dto.CharacterDTO;
import com.yago.Alkemy.dto.CharacterWithoutDetailDTO;
import com.yago.Alkemy.error.ApiException;
import com.yago.Alkemy.mapper.CharacterMapper;
import com.yago.Alkemy.model.Character;
import com.yago.Alkemy.repository.CharacterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional
public class CharacterService {

    private CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public List<CharacterWithoutDetailDTO> findAllWithoutDetail(){
        return findAll().stream().map(CharacterMapper::toCharactersDTO).collect(Collectors.toList());
    }

    public List<Character> findAll() {
        List<Character> characters = characterRepository.findAll();
        if(characters.isEmpty())
            throw new ApiException(HttpStatus.NOT_FOUND, "Not Found", "No characters found");
        return characters;
    }

    public List<?> findAll(Map<String, String> param) {
        if(param.isEmpty())
            return findAllWithoutDetail();

        Predicate<Character> predicate = character -> true;
        if(param.containsKey("name")){
            predicate = predicate.and(character -> character.getName().equals(param.get("name")));
        }else if(param.containsKey("age")){
            predicate = predicate.and(character -> ("" + character.getAge()).equals(param.get("age")));
        }else if(param.containsKey("movies")){
                predicate = predicate.and(character -> character.getMovies().stream()
                        .anyMatch(movie -> ("" + movie.getId()).equals(param.get("movies"))));
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid param", "Valid params are: name, age, moviesZ");
        }

        return findAll().stream().filter(predicate).collect(Collectors.toList());
    }

    public Character create(CharacterDTO characterDTO) {
        if(characterDTO.getId() != null){
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid body", "Cannot pass an id");
        }
        return save(characterDTO);
    }

    private Character save(CharacterDTO characterDTO) {
        Character character = CharacterMapper.toModel(characterDTO);
        return characterRepository.save(character);
    }

    public Character update(CharacterDTO characterDTO) {
        findById(characterDTO.getId());
        return save(characterDTO);
    }

    public Character findById(Long id) {
        if(id == null)
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid id", "The id cannot be null");
        Optional<Character> character = characterRepository.findById(id);
        if(!character.isPresent())
            throw new ApiException(HttpStatus.NOT_FOUND, "Not found", "Character not found with id: " + id);
        return character.get();
    }

    public Character delete(Long id) {
        Character character = findById(id);
        characterRepository.delete(character);
        return character;
    }
}
