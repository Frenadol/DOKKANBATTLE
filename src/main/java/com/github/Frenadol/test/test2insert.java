package com.github.Frenadol.test;

import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.entity.*;
import com.github.Frenadol.model.entity.Class;

public class test2insert {
    public static void main(String[] args) {
        Characters c=new Characters(1,Type.AGL, Class.SUPER,"Goku", Category.SAIYAN,"SuperAttack","UltraSuperAttack", Rarety.LR,"Ki+3ATQ+2000");
        CharactersDAO cDAo=new CharactersDAO();
        cDAo.save(c);
        System.out.println(c);
    }
    }

