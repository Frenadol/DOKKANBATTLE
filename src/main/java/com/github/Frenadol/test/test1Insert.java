package com.github.Frenadol.test;

import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.dao.UsersDAO;
import com.github.Frenadol.model.entity.*;


import java.util.ArrayList;

public class test1Insert {
    public static void main(String[] args) {
        ArrayList<Characters> characters = new ArrayList<>();
        Users u = new Users(1,"MIPOYAENMOTO","1234",characters,2345);
        UsersDAO udao= new UsersDAO();
        udao.save(u);
    }
}
