package com.github.Frenadol.test;

import  com.github.Frenadol.model.dao.AuthorDAO;

public class tests2SELECT {
    public static void main(String[] args) {
        AuthorDAO aDAO = new AuthorDAO();
        Author a = aDAO.findById("1");
        System.out.println(a);

        Author b=AuthorDAO.build().findById("1");


    }
}
