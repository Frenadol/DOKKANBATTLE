package com.github.Frenadol.test;

import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.dao.UsersDAO;
import com.github.Frenadol.model.entity.*;
import com.github.Frenadol.model.entity.Class;

public class test1Insert {
    public static void main(String[] args) {
        Users u = new Users(1,"MIPOYAENMOTO","1234",null,2345);

        UsersDAO udao= new UsersDAO();
        udao.save(u);
    }
}
