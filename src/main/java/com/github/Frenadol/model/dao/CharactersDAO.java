package com.github.Frenadol.model.dao;

import com.github.Frenadol.model.connection.ConnectionMariaDB;
import com.github.Frenadol.model.entity.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CharactersDAO implements DAO<Characters, String> {
    private final static String INSERT = "INSERT INTO characters (Id_character,Type,Class,Name,Categories,SuperAttack,UltraSuperAttack,Rarety,Passive,Visual) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private final static String UPDATE = "UPDATE characters SET Name where Id_character=?";
    private final static String FIND_BY_NAME = "SELECT * FROM characters where Name=?";
    private final static String FIND_BY_ID_CHARACTER = "SELECT * FROM characters where Id_character=?";
    private final static String FIND_BY_CATEGORY = "SELECT * FROM characters where Categories=?";
    private final static String FINDALL = "SELECT * FROM characters";
    private static final String DELETE = "DELETE FROM characters WHERE Id_character=?";
    private Connection conn;

    public CharactersDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Characters save(Characters entity) {
        Characters result = new Characters();
        if (entity == null || entity.getId_character() == 0) return result;
        Characters c = findById(entity);
        if (c == null) {
            //INSERT
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pst.setInt(1, entity.getId_character());
                pst.setString(2, entity.getType());
                pst.setString(3, entity.getCharacter_class());
                pst.setString(4, entity.getName());
                pst.setString(5, entity.getCategories());
                pst.setString(6, entity.getSuperAttack());
                pst.setString(7, entity.getUltraSuperAttack());
                pst.setString(8, entity.getRarety());
                pst.setString(9, entity.getPassive());
                pst.setBytes(10, entity.getVisual());
                pst.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            //UPDATE
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
                pst.setInt(1, entity.getId_character());
                pst.setString(2, entity.getType());
                pst.setString(3, entity.getCharacter_class());
                pst.setString(4, entity.getName());
                pst.setString(5, entity.getCategories());
                pst.setString(6, entity.getSuperAttack());
                pst.setString(7, entity.getUltraSuperAttack());
                pst.setString(8, entity.getRarety());
                pst.setString(9, entity.getPassive());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

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
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Characters findById(Characters id) {
        Characters result = null;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FIND_BY_ID_CHARACTER)) {
            pst.setInt(1, id.getId_character());
            try (ResultSet res = pst.executeQuery()) {
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
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public List<Characters> findAll() {
        List<Characters> result = new ArrayList<>();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Characters character = new Characters();
                character.setId_character(res.getInt("Id_character"));
                character.setType(res.getString("Type"));
                character.setCharacter_class(res.getString("Character_class"));
                character.setName(res.getString("Name"));
                character.setCategories(res.getString("Categories"));
                character.setSuperAttack(res.getString("SuperAttack"));
                character.setUltraSuperAttack(res.getString("UltraSuperAttack"));
                character.setRarety(res.getString("Rarety"));
                character.setPassive(res.getString("Passive"));
                result.add(character);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Characters> findByCategory(String category) {
        List<Characters> result = new ArrayList<>();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FIND_BY_CATEGORY)) {
            pst.setString(1, category);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Characters character = new Characters();
                character.setId_character(res.getInt("Id_character"));
                character.setType(res.getString("Type"));
                character.setCharacter_class(res.getString("Character_class"));
                character.setName(res.getString("Name"));
                character.setCategories(res.getString("Categories"));
                character.setSuperAttack(res.getString("SuperAttack"));
                character.setUltraSuperAttack(res.getString("UltraSuperAttack"));
                character.setRarety(res.getString("Rarety"));
                character.setPassive(res.getString("Passive"));
                result.add(character);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Characters delete(Characters entity) throws SQLException {
        if (entity != null) {
            try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
                pst.setString(1, String.valueOf(entity.getId_character()));
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                entity = null;
            }
        }
        return entity;
    }

    @Override
    public void close() throws IOException {

    }

    public static CharactersDAO build() {
        return new CharactersDAO();
    }
}
