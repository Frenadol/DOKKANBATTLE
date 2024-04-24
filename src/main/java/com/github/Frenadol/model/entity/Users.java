package com.github.Frenadol.model.entity;

import java.util.List;
import java.util.Objects;

public class Users {
    private int Id_user;
    private String Name_user;
    private String Password;
    private List<Characters> Characterslist;
    private int Dragon_stones;
    public Users(){

    }

    public Users(int id_user, String name_user, String password, List<Characters> characterslist, int dragon_stones) {
        Id_user = id_user;
        Name_user = name_user;
        Password = password;
        Characterslist = characterslist;
        Dragon_stones = dragon_stones;
    }

    public int getId_user() {
        return Id_user;
    }

    public void setId_user(int id_user) {
        Id_user = id_user;
    }

    public String getName_user() {
        return Name_user;
    }

    public void setName_user(String name_user) {
        Name_user = name_user;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public List<Characters> getCharacterslist() {
        return Characterslist;
    }

    public void setCharacterslist(List<Characters> characterslist) {
        Characterslist = characterslist;
    }

    public int getDragon_stones() {
        return Dragon_stones;
    }

    public void setDragon_stones(int dragon_stones) {
        Dragon_stones = dragon_stones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Id_user == users.Id_user;
    }


    @Override
    public int hashCode() {
        return Objects.hash(Id_user);
    }

    @Override
    public String toString() {
        return "Users{" +
                "Id_user=" + Id_user +
                ", Name_user='" + Name_user + '\'' +
                ", Password='" + Password + '\'' +
                ", Dragon_stones=" + Dragon_stones +
                '}';
    }
}
