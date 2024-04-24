package com.github.Frenadol.test;

import com.github.Frenadol.model.dao.BookDAO;
import  com.github.Frenadol.model.entity.Author;
import  com.github.Frenadol.model.entity.Book;

import java.util.ArrayList;

public class test1Insert {
    public static void main(String[] args) {
        Author a = new Author("1","Miguel de Cervantes",new ArrayList<>());
        Book b = new Book();
        b.setTitle("Don Quijote de la Mancha");
        b.setIsbn("1");
        b.setAuthor(a);
        //En JAVA tengo el libro listo
        BookDAO bDAO=new BookDAO();
        bDAO.save(b);
    }
}
