package com.github.Frenadol.model.dao;

import com.github.Frenadol.model.connection.ConnectionMariaDB;
import com.github.Frenadol.model.entity.*;
import com.github.Frenadol.view.CharacterListController;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class UsersDAO implements DAO<Users, String> {
    private static final String FINDBY_ID_USER = "SELECT * FROM users WHERE Id_user=?";
    private static final String INSERT = "INSERT INTO users (Name_user,Password,Dragon_stones,Admin) VALUES (?,?,?,?)";
    private static final String UPDATE = "UPDATE users SET Name_user=?, Password=?, Dragon_stones=?, Admin=? WHERE Id_user=?";
    private static final String DELETE = "DELETE FROM users WHERE Id_user=?";
    private static final String FIND_BY_NAME = "SELECT * FROM users WHERE Name_user=?";
    private static final String INSERT_OBTAINED = "INSERT INTO obtained (Id_user, Id_character) VALUES (?, ?)";
    private static final String DELETE_OBTAINED = "DELETE FROM obtained WHERE Id_user = ? AND Id_character = ?";
    private static final String FIND_ALL_OBTAINED = "SELECT c.* FROM characters c, obtained o WHERE c.Id_character = o.Id_character AND o.Id_user = ? GROUP BY c.Id_character";

    private Connection conn;

    public UsersDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Users save(Users entity) {
        if (entity == null) {
            return null;
        }
        if (findById(entity) == null) {
            insertUser(entity);
        } else {
            updateUser(entity);
        }
        return entity;
    }

    public void insertUser(Users entity) {
        try (PreparedStatement pst = conn.prepareStatement(INSERT)) {
            pst.setString(1, entity.getName_user());
            pst.setString(2, entity.getPassword());
            pst.setInt(3, entity.getDragon_stones());
            if (entity.isAdmin()) {
                pst.setInt(4, 1);
            } else {
                pst.setInt(4, 0);
            }
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertObtainedCharacters(Users user, int characterKey) {
        if (user.getId_user() != 0 && characterKey != 0) {
            try {
                List<Characters> obtainedCharacters = findAllCharacterFromObtained(user);
                boolean isCharacterObtained = obtainedCharacters.stream()
                        .anyMatch(character -> character.getId_character() == characterKey);
                if (!isCharacterObtained) {
                    try (PreparedStatement pst = conn.prepareStatement(INSERT_OBTAINED)) {
                        pst.setInt(1, user.getId_user());
                        pst.setInt(2, characterKey);
                        pst.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateUser(Users entity) {
        try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
            pst.setString(1, entity.getName_user());
            pst.setString(2, entity.getPassword());
            pst.setInt(3, entity.getDragon_stones());
            pst.setBoolean(4, entity.isAdmin());
            pst.setInt(5, entity.getId_user());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteObtainedCharacters(Users user, int characterKey) throws SQLException {
        try (PreparedStatement pst = conn.prepareStatement(DELETE_OBTAINED)) {
            pst.setInt(1, user.getId_user());
            pst.setInt(2, characterKey);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Characters> findAllCharacterFromObtained(Users users) throws SQLException {
        List<Characters> result = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(FIND_ALL_OBTAINED)) {
            pst.setInt(1, users.getId_user());
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
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
                    u.setName_user(res.getString("Name_user"));
                    u.setPassword(res.getString("Password"));
                    u.setDragon_stones(res.getInt("Dragon_stones"));
                    u.setAdmin(res.getBoolean("Admin"));
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
                result.setPassword(res.getString("Password"));
                result.setDragon_stones(res.getInt("Dragon_stones"));
                result.setAdmin(res.getBoolean("Admin"));
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

    public class UsersLazyAll extends Users {
        private static final String FIND_ALL = "SELECT * FROM Characters cha,obtained ob,Users us WHERE cha.Id_character=ob.Id_character AND ob.Id_user=us.Id_user AND us.Id_user=?";

        public UsersLazyAll(int id_user, String name_user, String password, int dragon_stones, List<Characters> characters_list, boolean admin) {
            super(id_user, name_user, password, dragon_stones, characters_list, admin);
        }

        public List<Characters> UsersLazyAll() {
            List<Characters> result = new ArrayList<>();
            if (super.getCharacters_list()!=null) {

                try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FIND_ALL)) {
                    pst.setInt(1,getId_user());
                    try (ResultSet res = pst.executeQuery()){
                        while (res.next()) {
                            Characters c = new Characters();
                            c.setId_character(res.getInt("Id_character"));
                            c.setType(res.getString("Type"));
                            c.setCharacter_class("Character_class");
                            c.setName(res.getString("Name"));
                            c.setCategories(res.getString("Categories"));
                            c.setSuperAttack(res.getString("SuperAttack"));
                            c.setUltraSuperAttack(res.getString("UltraSuperAttack"));
                            c.setRarety(res.getString("Rarety"));
                            c.setPassive(res.getString("Passive"));
                            c.setVisual(res.getBytes("Visual"));
                            result.add(c);
                    }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }
    }
}


