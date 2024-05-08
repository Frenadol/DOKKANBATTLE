package com.github.Frenadol.test;

import com.github.Frenadol.model.dao.CharactersDAO;
import com.github.Frenadol.model.entity.Characters;
import com.github.Frenadol.utils.ImageUtils;

public class Test2insert {
    public static void main(String[] args) {
        // Convertir la imagen en un array de bytes
        byte[] imageBytes = ImageUtils.imageToBytes("C:/Users/ferna/IdeaProjects/PROYECTODOKKANBATTLE/src/main/resources/MediaContent/ZamasuTEQ.jpeg");

        // Crear una nueva instancia de Characters
       Characters characters=new Characters(4,"TEQ","EXTREME","Infinite Zamasu","Sworn Enemies","Greatly raises ATK & DEF for 1 turn, causes colossal damage to enemy and greatly lowers DEF","Massively raises ATK and raises DEF for 1 turn, causes mega-colossal damage to enemy and greatly lowers ATK","LR","Activates the Entrance Animation (once only) and reduces damage received by 20% for 4 turns from the character's entry turn (plus an additional damage reduction of 20% for the 1st turn) when there is another \"Realm of Gods\" Category ally on the team upon the character's entry\n" +
               "Ki +6 and ATK & DEF +100%\n" +
               "Plus an additional Ki +3 and ATK & DEF +50% when there are no \"Realm of Gods\" Category enemies\n" +
               "High chance of performing a critical hit\n" +
               "Reduces damage received by 40%",imageBytes);
        // Guardar el personaje en la base de datos
        CharactersDAO cDAO = new CharactersDAO();
        cDAO.save(characters);

    }
}
