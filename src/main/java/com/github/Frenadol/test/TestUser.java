package com.github.Frenadol.test;

import com.github.Frenadol.model.dao.UsersDAO;
import com.github.Frenadol.model.entity.Users;

public class TestUser {
    public static void main(String[] args) {
        Users u = new Users(20,"PRUEBA","CONTRASEÑA",2323,null,false);
        UsersDAO uDAO = new UsersDAO();
        uDAO.save(u);
    }
}
