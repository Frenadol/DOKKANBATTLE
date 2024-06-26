package com.github.Frenadol.model.dao;

import com.github.Frenadol.model.connection.ConnectionMariaDB;
import com.github.Frenadol.model.entity.Character_portal;
import com.github.Frenadol.model.entity.Characters;
import com.github.Frenadol.model.entity.Users;
import com.github.Frenadol.utils.ErrorLog;
import com.github.Frenadol.view.SummonsMenuController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Character_portalDAO implements DAO<Character_portal, String> {
    private final static String INSERT = "INSERT INTO character_portal (Id_portal,Name_portal,Chance,Summon,BannerImage) VALUES (?,?,?,?,?)";
    private final static String UPDATE = "UPDATE character_portal SET Name_portal=? WHERE Id_portal=?";
    private final static String FIND_BY_NAME = "SELECT * FROM character_portal where Name_portal=?";
    private final static String FINDALL = "SELECT * from character_portal";
    private final static String FIND_BY_ID_PORTAL = "SELECT * FROM character_portal WHERE Id_portal=?";
    private final static String DELETE = "DELETE FROM character_portal WHERE Id_portal=?";
    private final static String FIND_IDS_FROM_LOCATED="SELECT cha.Name, cha.Visual, lo.Id_character FROM Characters cha, located lo WHERE lo.Id_character = cha.Id_character AND lo.Id_character = ?";
    private final static String FIND_CHARACTER_FROM_LOCATED="SELECT Id_portal,Id_character from located where Id_portal=? and Id_character=?";
    private static final String INSERT_LOCATED = "INSERT INTO located (Id_character, Id_portal) VALUES (?, ?)";
    private static final String DELETE_LOCATED = "DELETE FROM obtained WHERE Id_character=?";
    private Connection conn;

    public Character_portalDAO() {
        conn = ConnectionMariaDB.getConnection();
    }


    /**
     * Saves a Character_portal object to the database. If the object already exists, it is updated.
     * @param entity The Character_portal object to save.
     * @return The saved or updated Character_portal object.
     */
    public Character_portal save(Character_portal entity) {
        if (entity == null || entity.getId_portal() == 0) {
            return null;
        }
        try {
            if (findById(entity) == null) {
                insertCharacterPortal(entity);
            } else {
                updateCharacterPortal(entity);
                deleteLocated(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entity;
    }
    /**
     * Finds a character in the 'located' table based on the user ID and character ID.
     * @param idUser The user ID.
     * @param idCharacter The character ID.
     * @return The number of the character found.
     */
public int findCharacter(int idUser,int idCharacter){
        int numberCharacter=0;
        try(PreparedStatement pst=conn.prepareStatement(FIND_CHARACTER_FROM_LOCATED)){
            pst.setInt(1,idUser);
            pst.setInt(2,idCharacter);
            ResultSet res = pst.executeQuery();
            if(res.next()){
           numberCharacter=res.getInt("Id_character");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return numberCharacter;
}
    /**
     * Inserts a Character_portal object into the database.
     * @param entity The Character_portal object to insert.
     */
    public void insertCharacterPortal(Character_portal entity) {
        try (PreparedStatement pst = conn.prepareStatement(INSERT)) {
            pst.setInt(1, entity.getId_portal());
            pst.setString(2, entity.getName_portal());
            pst.setFloat(3, entity.getChance());
            pst.setInt(4, entity.getSummon());
            pst.setBytes(5, entity.getBannerImage());
            pst.executeUpdate();
        } catch (SQLException e) {
            ErrorLog.fileRead(e);
        }
    }
    /**
     * Inserts character data into the 'located' table.
     * @param entity The Character_portal object containing the character data.
     * @param idCharacter The character ID to insert.
     */
    public void insertIntoLocated(Character_portal entity, int idCharacter) {
        CharactersDAO charactersDAO = new CharactersDAO();
        List<Characters> characters = entity.getFeatured_characters();
        if (characters != null) {
            for (Characters character : characters) {
                Characters existingCharacter = charactersDAO.findById(character);
                if (existingCharacter != null) {
                    try (PreparedStatement pst = conn.prepareStatement(INSERT_LOCATED)) {
                        pst.setInt(1, character.getId_character());
                        pst.setInt(2, entity.getId_portal());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        ErrorLog.fileRead(e);
                    }
                }
            }
        }
    }
    /**
     * Finds all located characters by their ID.
     * @param id The ID of the character.
     * @return The Characters object found.
     */
public Characters findAllLocated(int  id) {
    Characters characters = new Characters();
    if (id > 0) {
        try (PreparedStatement pst = conn.prepareStatement(FIND_IDS_FROM_LOCATED)) {;
            pst.setInt(1, id);
            ResultSet set = pst.executeQuery();
            if (set.next()) {
                characters = new Characters();
                characters.setId_character(set.getInt("Id_character"));
                characters.setName(set.getString("Name"));
                characters.setVisual(set.getBytes("Visual"));

            }

        } catch (SQLException e) {
            ErrorLog.fileRead(e);

        }

    }

    return characters;
}

    /**
     * Updates a Character_portal object in the database.
     * @param entity The Character_portal object to update.
     */
    public void updateCharacterPortal(Character_portal entity) {
        try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
            pst.setString(1, entity.getName_portal());
            pst.setInt(2, entity.getId_portal());
            pst.executeUpdate();
        } catch (SQLException e) {
            ErrorLog.fileRead(e);
        }
    }
    /**
     * Deletes located character data for a given Character_portal object.
     * @param entity The Character_portal object whose located data is to be deleted.
     * @throws SQLException if a database access error occurs.
     */
    public void deleteLocated(Character_portal entity) throws SQLException {
        try (PreparedStatement pst = conn.prepareStatement(DELETE_LOCATED)) {
            pst.setInt(1, entity.getId_portal());
            pst.executeUpdate();
        } catch (SQLException e) {
            ErrorLog.fileRead(e);
        }
    }
    /**
     * Finds a Character_portal object by its ID.
     * @param id The ID of the Character_portal object.
     * @return The found Character_portal object, or null if not found.
     */
    @Override
    public Character_portal findById(Character_portal id) {
        Character_portal result = null;
        try (PreparedStatement pst = conn.prepareStatement(FIND_BY_ID_PORTAL)) {
            pst.setInt(1, id.getId_portal());
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    result = new Character_portal();
                    result.setId_portal(res.getInt("Id_portal"));
                    result.setName_portal(res.getString("Name_portal"));
                    result.setChance(res.getFloat("Chance"));
                    result.setSummon(res.getInt("Summon"));
                    result.setBannerImage(res.getBytes("BannerImage"));
                }
            }
        } catch (SQLException e) {
            ErrorLog.fileRead(e);
        }
        return result;
    }

    /**
     * Finds a Character_portal object by its name.
     * @param name The name of the Character_portal object.
     * @return The found Character_portal object, or null if not found.
     */
    @Override
    public Character_portal findByName(String name) {
        Character_portal result = null;
        try (PreparedStatement pst = conn.prepareStatement(FIND_BY_NAME)) {
            pst.setString(1, name);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    result = new Character_portal();
                    result.setId_portal(res.getInt("Id_portal"));
                    result.setName_portal(res.getString("Name_portal"));
                }
            }
        } catch (SQLException e) {
            ErrorLog.fileRead(e);
        }
        return result;
    }
    /**
     * Finds all Character_portal objects.
     * @return A list of all Character_portal objects.
     */
    @Override
    public List<Character_portal> findAll() {
        List<Character_portal> result = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(FINDALL)) {
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Character_portal character = new Character_portal();
                character.setId_portal(res.getInt("Id_portal"));
                character.setName_portal(res.getString("Name_portal"));
                result.add(character);
            }
            res.close();
        } catch (SQLException e) {
            ErrorLog.fileRead(e);
        }
        return result;
    }
    /**
     * Deletes a Character_portal object from the database.
     * @param entity The Character_portal object to delete.
     * @return The deleted Character_portal object, or null if the deletion failed.
     */
    @Override
    public Character_portal delete(Character_portal entity) {
        if (entity != null) {
            try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
                pst.setInt(1, entity.getId_portal());
                pst.executeUpdate();
            } catch (SQLException e) {
                ErrorLog.fileRead(e);
                entity = null;
            }
        }
        return entity;
    }



    /**
     * Closes the DAO, releasing any resources it may be holding.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void close() throws IOException {

    }
    /**
     * Builds and returns a new Character_portalDAO instance.
     * @return A new Character_portalDAO instance.
     */
    public static Character_portalDAO build() {
        return new Character_portalDAO();
    }
}
