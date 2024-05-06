package com.github.Frenadol.test;

import com.github.Frenadol.model.connection.ConnectionProperties;
import com.github.Frenadol.utils.XMLManager;

public class loadConnection {
    public static void main(String[] args) {
        ConnectionProperties c = XMLManager.readXML(new ConnectionProperties(), "connection.xml");
        System.out.println(c);
    }
}
