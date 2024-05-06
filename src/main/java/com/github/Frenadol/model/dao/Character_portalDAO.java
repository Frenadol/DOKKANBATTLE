package com.github.Frenadol.model.dao;

import com.github.Frenadol.model.connection.ConnectionMariaDB;
import com.github.Frenadol.model.entity.Character_portal;
import com.github.Frenadol.model.entity.Characters;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Character_portalDAO implements DAO<Character_portal, String> {
    private final static String INSERT = "INSERT INTO character_portal (Id_portal, Name_portal,Chance,Summon,BannerImage) VALUES (?,?,?,?,?)";
    private final static String UPDATE = "UPDATE character_portal SET Name_portal=? WHERE Id_portal=?";
    private final static String FIND_BY_NAME = "SELECT * FROM character_portal where Name_portal=?";
    private final static String FINDALL = "SELECT a.dni,a.name FROM author AS a";
    private final static String FIND_BY_ID_PORTAL = "SELECT Name_portal,Feeatured characters FROM character_portal WHERE Id_portal=?";
    private final static String DELETE = "DELETE FROM character_portal WHERE Id_portal=?";
    private static final String INSERT_LOCATED = "INSERT INTO located (Id_character, Id_portal) VALUES (?, ?)";
    private static final String DELETE_LOCATED = "DELETE FROM obtained WHERE Id_character =?";
    private Connection conn;

    public Character_portalDAO() {
        conn = ConnectionMariaDB.getConnection();
    }


    @Override
    public Character_portal save(Character_portal entity) {
        if (entity == null || entity.getId_portal() == 0) {
            return null;
        }
        try {
            if (findById(entity) == null) {
                insertCharacter_Portal(entity);
                insertIntoLocated(entity);
            } else {
                updateCharacterPortal(entity);
                deleteLocated(entity);
                insertIntoLocated(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entity;
    }

    public void insertCharacter_Portal(Character_portal entity) {
        try (PreparedStatement pst = conn.prepareStatement(INSERT)) {
            pst.setInt(1, entity.getId_portal());
            pst.setString(2, entity.getName_portal());
            pst.setFloat(3, entity.getChance());
            pst.setInt(4, entity.getSummon());
            pst.setBytes(5, entity.getBannerImage());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertIntoLocated(Character_portal entity) {
        List<Characters> characters = entity.getFeatured_characters();
        if (characters != null) {
            for (Characters character : characters) {
                try (PreparedStatement pst = conn.prepareStatement(INSERT_LOCATED)) {
                    pst.setInt(1, character.getId_character());
                    pst.setInt(2, entity.getId_portal());
                    pst.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateCharacterPortal(Character_portal entity) {
        try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
            pst.setString(1, entity.getName_portal());
            pst.setInt(2, entity.getId_portal());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteLocated(Character_portal entity) throws SQLException {
        try (PreparedStatement pst = conn.prepareStatement(DELETE_LOCATED)) {
            pst.setInt(1, entity.getId_portal());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

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
            e.printStackTrace();
        }
        return result;
    }

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
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public Character_portal delete(Character_portal entity) {
        if (entity != null) {
            try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
                pst.setInt(1, entity.getId_portal());
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

    public static Character_portalDAO build() {
        return new Character_portalDAO();
    }
}