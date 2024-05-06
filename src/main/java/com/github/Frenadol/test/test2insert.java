package com.github.Frenadol.test;

import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.entity.*;

public class Test2insert {
    public static void main(String[] args) {
        Characters c = new Characters(60, "FIS", "SUPER", "Gohan Ultimate", "Hybrids Saiyans", "Masenko", "Kamehameha", "LR", "KI +3 and ATQ +300 plus DEF+150 in first turn and stun the enemies and guards all attacks", null);
        CharactersDAO cDAo = new CharactersDAO();
        cDAo.save(c);
        System.out.println(c);
    }
}

