package com.github.Frenadol.model.dao;

import  com.github.Frenadol.model.connection.ConnectionMariaDB;
import com.github.Frenadol.model.entity.Characters;
import com.github.Frenadol.model.entity.Users;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO implements DAO<Users,String> {
    private static final String FINDALL ="SELECT b.isbn,b.title,b.id_author FROM book AS b";
    private static final String FINDBYID ="SELECT b.isbn,b.title,b.id_author FROM book AS b WHERE b.isbn=?";
    private static final String INSERT ="INSERT INTO book (isbn,title,id_author) VALUES (?,?,?)";
    private static final String UPDATE ="UPDATE book SET title=? WHERE isbn=?";
    private static final String DELETE ="DELETE FROM book WHERE isbn=?";
    private static final String FINDBYAUTHOR ="SELECT b.isbn,b.title,b.id_author FROM book AS b WHERE b.id_author=?";


    private Connection conn;
    public UsersDAO(){
        conn = ConnectionMariaDB.getConnection();
    }


    @Override
    public Users save(Users entity) {
        Users result=entity;
        if(entity!=null){
            int Id_user = entity.getId_user();
            if(Id_user!=0){
                Users isInDataBase =findById(entity);
                if(isInDataBase==null){
                    //INSERT
                    try(PreparedStatement pst = conn.prepareStatement(INSERT)) {
                        pst.setInt(1,entity.getId_user());
                        pst.setString(2,entity.getName_user());
                        pst.setString(3,entity.getPassword());
                        pst.setArray(4, conn.createArrayOf("VARCHAR", entity.getCharacterslist().toArray()));
                        pst.setInt(5,entity.getDragon_stones());
                        pst.executeUpdate();
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }else{
                    //UPDATE
                    try(PreparedStatement pst = conn.prepareStatement(UPDATE)) {
                        pst.setInt(1,entity.getId_user());
                        pst.setString(2,entity.getName_user());
                        pst.setString(3,entity.getPassword());
                        pst.setArray(4, conn.createArrayOf("VARCHAR", entity.getCharacterslist().toArray()));
                        pst.setInt(5,entity.getDragon_stones());
                        pst.executeUpdate();
                    }catch (SQLException e){
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
        try(PreparedStatement pst = conn.prepareStatement(FINDBYID)){
            pst.setInt(1,id.getId_user());
            try(ResultSet res = pst.executeQuery()){
                if(res.next()){
                    Users u = new Users();
                    u.setId_user(res.getInt("Id_user"));
                    //Eager
                    b.setAuthor(AuthorDAO.build().findById(res.getString("id_author")));
                    b.setTitle(res.getString("title"));
                    result=b;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}

    @Override
    public Users findByName(String name) {
        return null;
    }

    @Override
    public Users findByCategory(String category) {
        return null;
    }

    @Override
    public Users findByType(String type) {
        return null;
    }

    @Override
    public Users findByRarety(String rarety) {
        return null;
    }

    @Override
    public List<Users> findAll() {
        return null;
    }

    @Override
    public Users delete(Users entity) throws SQLException {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
