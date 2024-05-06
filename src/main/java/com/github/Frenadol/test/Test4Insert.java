package com.github.Frenadol.test;

import com.github.Frenadol.model.dao.Character_portalDAO;
import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.entity.Character_portal;
import com.github.Frenadol.model.entity.Characters;

import java.util.ArrayList;
import java.util.List;

public class Test4Insert {
    public static void main(String[] args) {
        Characters character = CharactersDAO.build().findByName("Goku Super Saiyan 3");
        Character_portal charactersPortals = new Character_portal(1, "EL poder del puno del dragon", null, 20, 10, null);
        List<Characters> FeaturedCharacters = new ArrayList<>();
        FeaturedCharacters.add(character);
        charactersPortals.setFeatured_characters(FeaturedCharacters);
        Character_portalDAO character_portalDAO = new Character_portalDAO();
        character_portalDAO.insertCharacter_Portal(charactersPortals);
        character_portalDAO.insertIntoLocated(charactersPortals);


    }
}



