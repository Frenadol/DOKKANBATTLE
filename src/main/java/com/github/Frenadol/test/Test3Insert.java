package com.github.Frenadol.test;

import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.dao.UsersDAO;
import com.github.Frenadol.model.entity.Characters;
import com.github.Frenadol.model.entity.Users;

import java.util.ArrayList;
import java.util.List;

public class Test3Insert {
    public static void main(String[] args) {
        Characters vhar = new Characters();
        Users testUser = new Users();
        testUser.setId_user(999);   
        vhar.setId_character(550);
        Characters character = CharactersDAO.build().findById(vhar);
        Users users = UsersDAO.build().findById(testUser);
        List<Characters> characters_list = new ArrayList<>();
        characters_list.add(character);
        testUser.setCharacters_list(characters_list);
        UsersDAO udao = new UsersDAO();
        udao.updateUser(testUser);
    }
}
