package com.github.Frenadol.test;

import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.entity.*;

public class test2insert {
    public static void main(String[] args) {
        Characters c=new Characters(1, "AGL", "EXTREME", "GOKU", "Saiyan", "kamehameha", "spirit bomb", "LR", "Activates the Entrance Animation upon the character's entry (once only) and Ki +4, ATK & DEF +59% and guards all attacks for 6 turns from the character's entry turn\n" +
                "Ki +4 and ATK & DEF +159%\n" +
                "Survives K.O. attacks (up to once within a turn)\n" +
                "Recovers 59% HP when HP is 40% or less at the end of turn (once only)\n" +
                "Plus an additional Ki +1 (up to +5) and chance of performing a critical hit +9% (up to 59%) with each attack received\n" +
                "Plus an additional DEF +59% starting from the turn in which the character receives the 5th attack in battle\n" +
                "Plus an additional ATK +120% starting from the turn in which the character receives the 9th attack in battle\n" +
                "High chance of nullifying Ki Blast Super Attacks directed at the character and countering with tremendous power");
        CharactersDAO cDAo=new CharactersDAO();
        cDAo.save(c);
        System.out.println(c);
    }
    }

