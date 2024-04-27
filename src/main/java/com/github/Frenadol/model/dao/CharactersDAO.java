package com.github.Frenadol.model.dao;

import com.github.Frenadol.model.connection.ConnectionMariaDB;
import com.github.Frenadol.model.entity.*;
import com.github.Frenadol.model.entity.Class;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CharactersDAO implements DAO<Characters,String> {
    private final static String INSERT = "INSERT INTO characters (Id_character,Type,Character_class,Name,Categories,SuperAttack,UltraSuperAttack,Rarety,Passive) VALUES (?,?,?,?,?,?,?,?,?)";
    private final static String UPDATE = "UPDATE characters SET name=? WHERE Id_character=?";
    private final static String FIND_BY_NAME = "SELECT FROM characters where Name=?";
    private final static String FIND_BY_ID_CHARACTER = "SELECT FROM characters where Id_character=?";
    private final static String FIND_BY_CATEGORY = "SELECT FROM characters where Categories=?";
    private final static String FINDALL = "SELECT * FROM character";
    private static final String DELETE = "DELETE FROM characters WHERE Id_character=?";
    private Connection conn;

    public CharactersDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Characters save(Characters entity) {
        Characters result = entity;
        if (entity == null || entity.getId_character() == 0) return result;
        Characters c = findById(entity);  //si no está devuelve un autor por defecto
        if (c.getId_character() == 0) {
            //INSERT
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pst.setInt(1, entity.getId_character());
                pst.setString(2, entity.getName());
                pst.setString(3, String.valueOf(entity.getCharacter_class()));
                pst.setString(4, entity.getName());
                pst.setString(5, String.valueOf(entity.getCategories()));
                pst.setString(6, entity.getSuperAttack());
                pst.setString(7, entity.getUltraSuperAttack());
                pst.setString(8, String.valueOf(entity.getRarety()));
                pst.setString(9, entity.getPassive());
                pst.executeUpdate();
                ResultSet res = pst.getGeneratedKeys();
                if (res.next()) {
                    entity.setId_character(res.getInt(1));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            //UPDATE
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
                pst.setInt(1, entity.getId_character());
                pst.setString(2, String.valueOf(entity.getType()));
                pst.setString(3, String.valueOf(entity.getCharacter_class()));
                pst.setString(4, entity.getName());
                pst.setString(5, String.valueOf(entity.getCategories()));
                pst.setString(6, entity.getSuperAttack());
                pst.setString(7, entity.getUltraSuperAttack());
                pst.setString(8, String.valueOf(entity.getRarety()));
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
                result.setType(Type.valueOf(res.getString("Type")));
                result.setCharacter_class(Class.valueOf(res.getString("Character_class")));
                result.setName(res.getString("Name"));
                result.setCategories(Category.valueOf(res.getString("Categories")));
                result.setSuperAttack(String.valueOf(Type.valueOf(res.getString("SuperAttack"))));
                result.setUltraSuperAttack(String.valueOf(Type.valueOf(res.getString("UltraSuperAttack"))));
                result.setRarety((Rarety) res.getObject("Rarety"));
                result.setPassive(res.getString("Passive"));
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
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                result.setId_character(res.getInt("Id_character"));
                result.setType(Type.valueOf(res.getString("Type")));
                result.setCharacter_class(Class.valueOf(res.getString("Character_class")));
                result.setName(res.getString("Name"));
                result.setCategories(Category.valueOf(res.getString("Categories")));
                result.setSuperAttack(String.valueOf(Type.valueOf(res.getString("SuperAttack"))));
                result.setUltraSuperAttack(String.valueOf(Type.valueOf(res.getString("UltraSuperAttack"))));
                result.setRarety((Rarety) res.getObject("Rarety"));
                result.setPassive(res.getString("Passive"));
                //Lazy
                //BookDAO bDAO = new BookDAO();
                //result.setBooks(bDAO.findByAuthor(result));
            }
            res.close();
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
                character.setType(Type.valueOf(res.getString("Type")));
                character.setCharacter_class(Class.valueOf(res.getString("Character_class")));
                character.setName(res.getString("Name"));
                character.setCategories(Category.valueOf(res.getString("Categories")));
                character.setSuperAttack(String.valueOf(Type.valueOf(res.getString("SuperAttack"))));
                character.setUltraSuperAttack(String.valueOf(Type.valueOf(res.getString("UltraSuperAttack"))));
                character.setRarety((Rarety) res.getObject("Rarety"));
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
                character.setType(Type.valueOf(res.getString("Type")));
                character.setCharacter_class(Class.valueOf(res.getString("Character_class")));
                character.setName(res.getString("Name"));
                character.setCategories(Category.valueOf(res.getString("Categories")));
                character.setSuperAttack(String.valueOf(Type.valueOf(res.getString("SuperAttack"))));
                character.setUltraSuperAttack(String.valueOf(Type.valueOf(res.getString("UltraSuperAttack"))));
                character.setRarety((Rarety) res.getObject("Rarety"));
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

    class CharactersLazy extends Characters {

    }
}

