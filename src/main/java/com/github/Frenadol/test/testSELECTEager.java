package com.github.Frenadol.test;

import  com.github.Frenadol.model.dao.BookDAO;

public class testSELECTEager {
    public static void main(String[] args) {
        Book b=BookDAO.build().findById("1");
        System.out.println(b);
    }
}
