package com.github.Frenadol.test;

import  com.github.Frenadol.model.dao.BookDAO;

import java.util.List;

public class testSELECT {
    public static void main(String[] args) {
        BookDAO bDAO = new BookDAO();
        Author a = new Author();
        a.setDni("1");
        List<Book> books = bDAO.findByAuthor(a);
        System.out.println(books);
    }
}
