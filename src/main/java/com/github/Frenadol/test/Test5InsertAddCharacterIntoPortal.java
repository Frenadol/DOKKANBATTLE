package com.github.Frenadol.test;

import com.github.Frenadol.model.dao.Character_portalDAO;
import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.entity.Character_portal;
import com.github.Frenadol.model.entity.Characters;

import java.util.ArrayList;
import java.util.List;

public class Test5InsertAddCharacterIntoPortal {
    public static void main(String[] args) {
        Character_portal portalTest=new Character_portal();
        Characters character1=new Characters();
        character1.setId_character(5);
        portalTest.setId_portal(2);
       Character_portal charactersPortals=Character_portalDAO.build().findById(portalTest);
        Characters CharacterTest = CharactersDAO.build().findById(character1);
        List<Characters> characters_list = new ArrayList<>();
        characters_list.add(CharacterTest);
        charactersPortals.setFeatured_characters(characters_list);
        Character_portalDAO CDAO = new Character_portalDAO();
        CDAO.insertIntoLocated(charactersPortals);
        CDAO.updateCharacterPortal(charactersPortals);
    }
}
