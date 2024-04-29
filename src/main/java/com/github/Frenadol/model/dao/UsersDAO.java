package com.github.Frenadol.model.dao;

import  com.github.Frenadol.model.connection.ConnectionMariaDB;
import com.github.Frenadol.model.entity.*;
import com.github.Frenadol.model.entity.Class;

import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.List;

public class UsersDAO implements DAO<Users,String> {
    private static final String FINDBY_ID_USER = "SELECT users FROM users WHERE Id_user=?";
    private static final String INSERT = "INSERT INTO users (Id_user,Name_user,Password,Character_list,Dragon_stones) VALUES (?,?,?,?,?)";
    private static final String UPDATE = "UPDATE users SET name_user=? WHERE Id_user=?";
    private static final String DELETE = "DELETE FROM users WHERE Id_user=?";
    private static final String FIND_BY_NAME = "SELECT users FROM users WHERE Name_user=?";

    private Connection conn;

    public UsersDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Users save(Users entity) {
        Users result = entity;
        if (entity != null) {
            int Id_user = entity.getId_user();
            if (Id_user != 0) {
                Users isInDataBase = findById(entity);
                if (isInDataBase == null) {
                    //INSERT
                    try (PreparedStatement pst = conn.prepareStatement(INSERT)) {
                        pst.setInt(1, entity.getId_user());
                        pst.setString(2, entity.getName_user());
                        pst.setString(3, entity.getPassword());
                        pst.setString(4,entity.getCharacterslist().toString());
                        pst.setInt(5, entity.getDragon_stones());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    //UPDATE
                    try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
                        pst.setInt(1, entity.getId_user());
                        pst.setString(2, entity.getName_user());
                        pst.setString(3, entity.getPassword());
                        pst.setArray(4, conn.createArrayOf("VARCHAR", entity.getCharacterslist().toArray()));
                        pst.setInt(5, entity.getDragon_stones());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
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
                    //Eager
                    u.setName_user(res.getString("Name_user"));
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
    public Users delete(Users entity) throws SQLException {
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
}
