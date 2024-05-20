package com.github.Frenadol.test;

import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.dao.UsersDAO;
import com.github.Frenadol.model.entity.*;

import java.util.ArrayList;
import java.util.List;

public class test1Insert {
    public static void main(String[] args) {
        Characters characters=new Characters();
        Users user = new Users();
        user.setId_user(1);
        characters.setId_character(1);
        Characters character = CharactersDAO.build().findById(characters);
        Users testUser = UsersDAO.build().findById(user);
        List<Characters> characters_list = new ArrayList<>();
        characters_list.add(character);
        testUser.setCharacters_list(characters_list);
        UsersDAO udao = new UsersDAO();
        udao.updateUser(testUser);
    }
}
