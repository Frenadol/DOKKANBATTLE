package com.github.Frenadol.test;

import com.github.Frenadol.model.dao.AuthorDAO;
import com.github.Frenadol.model.entity.Author;
import com.github.Frenadol.model.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class test2Insert {
    public static void main(String[] args) {
        AuthorDAO aDAO = new AuthorDAO();
        List<Author> as = aDAO.findAll();
        System.out.println(as);

        Author a1 = aDAO.findById("1");
        System.out.println(a1);

        Author a2 = new Author("2","Neruda",new ArrayList<>());
        a2.addBook(new Book("12","Crepusculaio",a2));
        aDAO.save(a2);

        as = aDAO.findAll();
        System.out.println(as);
    }
}
