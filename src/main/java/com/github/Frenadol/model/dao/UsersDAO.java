package com.github.Frenadol.model.dao;

import com.github.Frenadol.model.connection.ConnectionMariaDB;
import com.github.Frenadol.model.entity.*;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class UsersDAO implements DAO<Users, String> {
    private static final String FINDBY_ID_USER = "SELECT * FROM users WHERE Id_user=?";
    private static final String INSERT = "INSERT INTO users (Id_user,Name_user,Password,Dragon_stones,Admin) VALUES (?,?,?,?,?)";
    private static final String UPDATE = "UPDATE users SET Name_user=? WHERE Id_user=?";
    private static final String DELETE = "DELETE FROM users WHERE Id_user=?";
    private static final String FIND_BY_NAME = "SELECT * FROM users WHERE Name_user=?";
    private static final String INSERT_OBTAINED = "INSERT INTO obtained (Id_user, Id_character) VALUES (?, ?)";
    private static final String DELETE_OBTAINED = "DELETE FROM obtained WHERE Id_character = ?";

    private Connection conn;

    public UsersDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Users save(Users entity) {
        if (entity == null || entity.getId_user() == 0) {
            return null;
        }
        try {
            if (findById(entity) == null) {
                insertUser(entity);
                insertObtainedCharacters(entity);
            } else {
                updateUser(entity);
                deleteObtainedCharacters(entity);
                insertObtainedCharacters(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entity;
    }

    public void insertUser(Users entity) {
        try (PreparedStatement pst = conn.prepareStatement(INSERT)) {
            pst.setInt(1, entity.getId_user());
            pst.setString(2, entity.getName_user());
            pst.setString(3, entity.getPassword());
            pst.setInt(4, entity.getDragon_stones());
            if (entity.isAdmin() == true) {
                pst.setInt(5, 1);
            } else {
                pst.setInt(5, 0);
            }
            pst.setBoolean(5, entity.isAdmin());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertObtainedCharacters(Users entity) {
        List<Characters> characters = entity.getCharacters_list();
        if (characters != null) {
            for (Characters character : characters) {
                try (PreparedStatement pst = conn.prepareStatement(INSERT_OBTAINED)) {
                    pst.setInt(1, entity.getId_user());
                    pst.setInt(2, character.getId_character());
                    pst.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateUser(Users entity) {
        try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
            pst.setInt(1, entity.getId_user());
            pst.setString(2, entity.getName_user());
            pst.setString(3, entity.getPassword());
            pst.setInt(4, entity.getDragon_stones());
            if (entity.isAdmin() == true) {
                pst.setInt(5, 1);
            } else {
                pst.setInt(5, 0);
            }
            pst.setBoolean(5, entity.isAdmin());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteObtainedCharacters(Users entity) throws SQLException {
        try (PreparedStatement pst = conn.prepareStatement(DELETE_OBTAINED)) {
            pst.setInt(1, entity.getId_user());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public Users findById(Users id) {
        Users result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBY_ID_USER)) {
            pst.setInt(1, id.getId_user());
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    Users u = new Users();
                    u.setId_user(res.getInt("Id_user"));
                    result = u;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public Users findByName(String name) {
        Users result = null;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FIND_BY_NAME)) {
            pst.setString(1, name);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                result = new Users();
                result.setId_user(res.getInt("Id_user"));
                result.setName_user(res.getString("Name_user"));
                result.setDragon_stones(res.getInt("Dragon_stones"));
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<Users> findAll() {
        return null;
    }

    @Override
    public Users delete(Users entity) {
        if (entity != null) {
            try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
                pst.setString(1, String.valueOf(entity.getId_user()));
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

    public static UsersDAO build() {
        return new UsersDAO();
    }
}


