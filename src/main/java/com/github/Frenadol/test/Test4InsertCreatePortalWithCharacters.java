package com.github.Frenadol.test;

import com.github.Frenadol.model.dao.Character_portalDAO;
import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.entity.Character_portal;
import com.github.Frenadol.model.entity.Characters;

import java.util.ArrayList;
import java.util.List;

public class Test4InsertCreatePortalWithCharacters {
    public static void main(String[] args) {
        Characters character = CharactersDAO.build().findByName("Cooler Final Form");
        Character_portal charactersPortals = Character_portalDAO.build().findByName("Dokkan Festival(Goku Black Super Saiyan Rose)");
        List<Characters> FeaturedCharacters = new ArrayList<>();
        FeaturedCharacters.add(character);
        charactersPortals.setFeatured_characters(FeaturedCharacters);
        Character_portalDAO character_portalDAO = new Character_portalDAO();
         character_portalDAO.updateCharacterPortal(charactersPortals);


    }
}



