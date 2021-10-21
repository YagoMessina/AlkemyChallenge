package com.yago.Alkemy.controller;

import com.yago.Alkemy.dto.CharacterDTO;
import com.yago.Alkemy.dto.CharacterWithoutDetailDTO;
import com.yago.Alkemy.model.Character;
import com.yago.Alkemy.service.CharacterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    private CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    public ResponseEntity<List<CharacterWithoutDetailDTO>> findAllWithoutDetail(){
        return ResponseEntity.ok(characterService.findAllWithoutDetail());
    }

    @GetMapping("/detailed")
    ResponseEntity<List<Character>> findAll(){
        return ResponseEntity.ok(characterService.findAll());
    }

    @PostMapping
    public ResponseEntity<URI> create(@RequestBody @Valid CharacterDTO characterDTO) {
        Character character = characterService.create(characterDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(character.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Character> read(@PathVariable Long id) {
        Character character = characterService.findById(id);
        return ResponseEntity.ok(character);
    }

    @PutMapping
    public ResponseEntity<Character> update(@RequestBody @Valid CharacterDTO characterDTO) {
        Character character = characterService.update(characterDTO);
        return ResponseEntity.ok(character);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        characterService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
