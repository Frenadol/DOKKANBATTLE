package com.github.Frenadol.test;

import com.github.Frenadol.model.dao.Character_portalDAO;
import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.entity.Character_portal;
import com.github.Frenadol.model.entity.Characters;
import com.github.Frenadol.utils.ImageUtils;

public class Test2insert {
    public static void main(String[] args) {
        // Convertir la imagen en un array de bytes
        byte[] imageBytes = ImageUtils.imageToBytes("C:/Users/ferna/IdeaProjects/PROYECTODOKKANBATTLE/src/main/resources/MediaContent/GokuBlackBanner.jpeg");

        // Crear una nueva instancia de Characters
       Character_portal character_portal = new Character_portal(2,"Dokkan Festival(Goku Black Super Saiyan Rose)",null,20,10,imageBytes);
        Character_portalDAO cDAO = new Character_portalDAO();
        cDAO.save(character_portal);

    }
}
