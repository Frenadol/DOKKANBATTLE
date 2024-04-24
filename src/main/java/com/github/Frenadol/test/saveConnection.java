package com.github.Frenadol.test;

import com.github.Frenadol.model.connection.ConnectionProperties;
import  com.github.Frenadol.utils.XMLManager;

public class saveConnection {
    public static void main(String[] args) {
        ConnectionProperties c = new ConnectionProperties("localhost","3306","library","root","root");
        XMLManager.writeXML(c,"connection.xml");
    }
}
