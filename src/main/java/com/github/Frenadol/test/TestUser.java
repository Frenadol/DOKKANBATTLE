package com.github.Frenadol.test;

import com.github.Frenadol.model.dao.Character_portalDAO;
import com.github.Frenadol.model.dao.UsersDAO;
import com.github.Frenadol.model.entity.Character_portal;
import com.github.Frenadol.model.entity.Users;
import com.github.Frenadol.utils.ImageUtils;

public class TestUser {
    public static void main(String[] args) {
        byte[] imageBytes = ImageUtils.
                imageToBytes("C:/Users/ferna/IdeaProjects/PROYECTODOKKANBATTLE/src/main/resources/MediaContent/GokuBlackBanner.jpeg");

        Character_portal c = new Character_portal(2,"Dokkan Festival(Goku Black Super Saiyan Rose)",null,20,10,imageBytes);
        Character_portalDAO characterPortalDAO= new Character_portalDAO();
        characterPortalDAO.build().save(c);
    }
}
