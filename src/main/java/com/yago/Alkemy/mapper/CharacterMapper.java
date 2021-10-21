package com.yago.Alkemy.mapper;

import com.yago.Alkemy.dto.CharacterDTO;
import com.yago.Alkemy.dto.CharacterWithoutDetailDTO;
import com.yago.Alkemy.model.Character;

import java.util.HashSet;

public class CharacterMapper {

    public static Character toModel(CharacterDTO characterDTO){
        return new Character(characterDTO.getId(), characterDTO.getImage(), characterDTO.getName(),
                characterDTO.getAge(), characterDTO.getWeight(),
                characterDTO.getStory(), new HashSet<>());
    }

    public static CharacterWithoutDetailDTO toCharactersDTO(Character character){
        return new CharacterWithoutDetailDTO(character.getImage(), character.getName());
    }
}
