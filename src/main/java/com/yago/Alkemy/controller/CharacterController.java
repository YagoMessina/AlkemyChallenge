package com.yago.Alkemy.controller;

import com.yago.Alkemy.dto.CharacterDTO;
import com.yago.Alkemy.model.Character;
import com.yago.Alkemy.service.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    private CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @Operation(summary = "List of characters", description = "param are optional")
    @GetMapping()
    public ResponseEntity<?> find(@Schema(defaultValue = "{\n" +
            "  \"name\": \"string\",\n" +
            "  \"age\": \"0\",\n" +
            "  \"movies\": \"0\"\n" +
            "}") @RequestParam Map<String, String> param){
        return ResponseEntity.ok(characterService.findAll(param));
    }

    @Operation(summary = "Detailed list of characters")
    @GetMapping("/detailed")
    public ResponseEntity<List<Character>> findAll(){
        return ResponseEntity.ok(characterService.findAll());
    }

    @Operation(summary = "Character creation", description = "Do not pass an id")
    @PostMapping
    public ResponseEntity<URI> create(@RequestBody @Valid CharacterDTO characterDTO) {
        Character character = characterService.create(characterDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(character.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Get character by id")
    @GetMapping("/{id}")
    public ResponseEntity<Character> read(@PathVariable Long id) {
        Character character = characterService.findById(id);
        return ResponseEntity.ok(character);
    }

    @Operation(summary = "Character update")
    @PutMapping
    public ResponseEntity<Character> update(@RequestBody @Valid CharacterDTO characterDTO) {
        Character character = characterService.update(characterDTO);
        return ResponseEntity.ok(character);
    }

    @Operation(summary = "Character delete")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        characterService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
