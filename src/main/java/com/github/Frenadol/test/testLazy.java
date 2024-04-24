package com.github.Frenadol.test;

import  com.github.Frenadol.model.dao.AuthorDAO;
import  com.github.Frenadol.model.entity.Author;

public class testLazy {
    public static void main(String[] args) {
        Author a = AuthorDAO.build().findById("1");
        System.out.println(a);
        System.out.println(a.getBooks());
        System.out.println(a.getBooks());
    }
}
