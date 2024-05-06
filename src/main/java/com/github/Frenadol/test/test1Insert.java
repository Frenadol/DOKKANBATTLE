package com.github.Frenadol.test;

import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.dao.UsersDAO;
import com.github.Frenadol.model.entity.*;

import java.util.ArrayList;
import java.util.List;

public class test1Insert {
    public static void main(String[] args) {
        Characters characters=new Characters();
        characters.setId_character(60);
        Characters character = CharactersDAO.build().findById(characters);
        Users testUser = UsersDAO.build().findByName("Pepito");
        List<Characters> characters_list = new ArrayList<>();
        characters_list.add(character);
        testUser.setCharacters_list(characters_list);
        UsersDAO udao = new UsersDAO();
        udao.insertObtainedCharacters(testUser);
        udao.updateUser(testUser);


    }
}
