package com.github.Frenadol.model.dao;

import com.github.Frenadol.model.connection.ConnectionMariaDB;
import com.github.Frenadol.model.entity.Characters;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CharactersDAO implements DAO<Characters,String> {
    private final static String INSERT="INSERT INTO characters (Id_character,type,Character_class,Name,Categories,SuperAttack,UltraSuperAttack,Rarety,Passive) VALUES (?,?,?,?,?,?,?,?,?)";
    private final static String UPDATE="UPDATE author SET name=? WHERE dni=?";
    private final static String FINDALL="SELECT a.dni,a.name FROM author AS a";
    private final static String FINDBYIDCHARACTER="SELECT a.dni,a.name FROM author AS a WHERE a.dni=?";
    private final static String DELETE="DELETE FROM author AS a WHERE a.dni=?";

public Characters saveCharacter(Characters entity) {
    Characters result = entity;
    if (entity == null || entity.getId_character() == 0) return result;
    Characters c = findById_character(entity);  //si no está devuelve un autor por defecto
    if (c.getId_character() == 0) {
        //INSERT
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, entity.getId_character());
            pst.setString(2, entity.getName());
            pst.executeUpdate();
                          ResultSet res = pst.getGeneratedKeys();
                            if(res.next()){
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
            pst.setString(4,entity.getName());
            pst.setString(5, String.valueOf(entity.getCategories()));
            pst.setString(6,entity.getSuperAttack());
            pst.setString(7,entity.getUltraSuperAttack());
            pst.setString(8, String.valueOf(entity.getRarety()));
            pst.setString(9,entity.getPassive());


            pst.executeUpdate();

            //update cascada --> opcional
            if (entity.getBooks() != null) {
                List<Book> booksBefore = BookDAO.build().findByAuthor(entity);
                List<Book> booksAfter = entity.getBooks();

                List<Book> booksToBeRemoved = new ArrayList<>(booksBefore);
                booksToBeRemoved.removeAll(booksAfter);

                for (Book b : booksToBeRemoved) {
                    BookDAO.build().delete(b);
                }
                for (Book b : booksAfter) {
                    BookDAO.build().save(b);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return result;
}

    @Override
    public Characters findById_character(Characters id) {
        return null;
    }



    @Override
    public Characters findByName(String name) {
        return null;
    }

    @Override
    public Characters findByCategory(String category) {
        return null;
    }

    @Override
    public Characters findByType(String type) {
        return null;
    }

    @Override
    public Characters findByRarety(String rarety) {
        return null;
    }


    @Override
    public List<Characters> findAll() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
