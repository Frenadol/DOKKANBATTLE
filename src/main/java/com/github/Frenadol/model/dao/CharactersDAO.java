package com.github.Frenadol.model.dao;

import com.github.Frenadol.model.connection.ConnectionMariaDB;
import com.github.Frenadol.model.entity.*;
import com.github.Frenadol.utils.ErrorLog;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CharactersDAO implements DAO<Characters,String> {
    private final static String INSERT = "INSERT INTO characters (Id_character,Type,Class,Name,Categories,SuperAttack,UltraSuperAttack,Rarety,Passive,Visual) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private final static String UPDATE = "UPDATE characters SET Name=?, Passive=? WHERE Id_character=?";
    private final static String FIND_BY_NAME = "SELECT * FROM characters where Name=?";
    private final static String COUNT_CHARACTERS = "SELECT COUNT(*) FROM characters";

    private final static String FIND_BY_ID_CHARACTER = "SELECT * FROM characters where Id_character=?";
    private final static String FIND_BY_PASSIVE = "SELECT * FROM characters where Passive=?";
    private final static String FINDALL = "SELECT * FROM characters";
    private static final String DELETE = "DELETE FROM characters WHERE Id_character=?";
    private Connection conn;

    public CharactersDAO() {
        conn = ConnectionMariaDB.getConnection();
    }
    /**
     * Saves a Characters object to the database. If the object already exists, it is updated.
     * @param entity The Characters object to save.
     * @return The saved or updated Characters object.
     */
    @Override
    public Characters save(Characters entity) {
        Characters result = new Characters();
        if (entity == null || entity.getId_character() == 0) return result;
        Characters c = findById(entity);
        if (c == null){
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS)) {
               pst.setInt(1, entity.getId_character());
               pst.setString(2, entity.getType());
                pst.setString(3, entity.getCharacter_class());
                pst.setString(4, entity.getName());
                pst.setString(5, entity.getCategories());
                pst.setString(6, entity.getSuperAttack());
                pst.setString(7, entity.getUltraSuperAttack());
                pst.setString(8, entity.getRarety());
                pst.setString(9, entity.getPassive());
                pst.setBytes(10,entity.getVisual());
                pst.executeUpdate();
            } catch (SQLException e) {
                ErrorLog.fileRead(e);
            }
        } else {
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
                pst.setString(1, entity.getName());
                pst.setString(2, entity.getPassive());
                pst.setInt(3, entity.getId_character());
                pst.executeUpdate();
            } catch (SQLException e) {
                ErrorLog.fileRead(e);
            }
        }
        return result;
    }
    /**
     * Finds a Characters object by its passive.
     * @param passive The passive attribute to search for.
     * @return The found Characters object, or null if not found.
     */
    public Characters findByPassive(String passive) {
        Characters result = null;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FIND_BY_PASSIVE)) {
            pst.setString(1, passive);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                result = new Characters();
                result.setId_character(res.getInt("Id_character"));
                result.setType(res.getString("Type"));
                result.setCharacter_class("Character_class");
                result.setName(res.getString("Name"));
                result.setCategories(res.getString("Categories"));
                result.setSuperAttack(res.getString("SuperAttack"));
                result.setUltraSuperAttack(res.getString("UltraSuperAttack"));
                result.setRarety(res.getString("Rarety"));
                result.setPassive(res.getString("Passive"));
                result.setVisual(res.getBytes("Visual"));
            }
            res.close();
        } catch (SQLException e) {
            ErrorLog.fileRead(e);
        }
        return result;
    }
    /**
     * Finds a Characters object by its name.
     * @param name The name to search for.
     * @return The found Characters object, or null if not found.
     */
    @Override
    public Characters findByName(String name) {
        Characters result = null;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FIND_BY_NAME)) {
            pst.setString(1, name);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                result = new Characters();
                result.setId_character(res.getInt("Id_character"));
                result.setType(res.getString("Type"));
                result.setCharacter_class("Character_class");
                result.setName(res.getString("Name"));
                result.setCategories(res.getString("Categories"));
                result.setSuperAttack(res.getString("SuperAttack"));
                result.setUltraSuperAttack(res.getString("UltraSuperAttack"));
                result.setRarety(res.getString("Rarety"));
                result.setPassive(res.getString("Passive"));
                result.setVisual(res.getBytes("Visual"));
            }
            res.close();
        } catch (SQLException e) {
            ErrorLog.fileRead(e);
        }
        return result;
    }
    /**
     * Counts the total number of characters in the database.
     * @return The total number of characters.
     */
    public int countCharacters() {
        int count = 0;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(COUNT_CHARACTERS)) {
            try(ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    count = res.getInt(1);
                }
            }
        } catch (SQLException e) {
            ErrorLog.fileRead(e);
        }
        return count;
    }
    /**
     * Finds a Characters object by its ID.
     * @param id The ID of the Characters object.
     * @return The found Characters object, or null if not found.
     */
    @Override
    public Characters findById(Characters id) {
        Characters result=null;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FIND_BY_ID_CHARACTER)) {
            pst.setInt(1, id.getId_character());
            try(ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    Characters c = new Characters();
                    c.setId_character(res.getInt("Id_character"));
                    c.setType(res.getString("Type"));
                    c.setCharacter_class(res.getString("Class"));
                    c.setName(res.getString("Name"));
                    c.setCategories(res.getString("Categories"));
                    c.setSuperAttack(res.getString("SuperAttack"));
                    c.setUltraSuperAttack(res.getString("UltraSuperAttack"));
                    c.setRarety(res.getString("Rarety"));
                    c.setPassive(res.getString("Passive"));
                    c.setVisual(res.getBytes("Visual"));
                    result = c;
                }
            }
        } catch (SQLException e) {
            ErrorLog.fileRead(e);
        }
        return result;
    }
    /**
     * Finds all Characters objects in the database.
     * @return A list of all Characters objects.
     */
    @Override
    public List<Characters> findAll() {
        List<Characters> result = new ArrayList<>();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Characters character = new Characters();
                character.setId_character(res.getInt("Id_character"));
                character.setType(res.getString("Type"));
                character.setCharacter_class(res.getString("Class"));
                character.setName(res.getString("Name"));
                character.setCategories(res.getString("Categories"));
                character.setSuperAttack(res.getString("SuperAttack"));
                character.setUltraSuperAttack(res.getString("UltraSuperAttack"));
                character.setRarety(res.getString("Rarety"));
                character.setPassive(res.getString("Passive"));
                character.setVisual(res.getBytes("Visual"));
                result.add(character);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * Deletes a Characters object from the database.
     * @param entity The Characters object to delete.
     * @return The deleted Characters object, or null if the deletion failed.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public Characters delete(Characters entity) throws SQLException {
        if (entity != null) {
            try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
                pst.setString(1, String.valueOf(entity.getId_character()));
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
     * Builds and returns a new CharactersDAO instance.
     * @return A new CharactersDAO instance.
     */
    public static CharactersDAO build(){
        return new CharactersDAO();
    }
}
