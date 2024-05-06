package com.github.Frenadol.model.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Characters {
    private int Id_character;
    private String Type;
    private String Character_class;
    private String Name;
    private String categories;
    private String SuperAttack;
    private String UltraSuperAttack;
    private String Rarety;
    private String Passive;
    private byte[] Visual;

    public Characters() {

    }

    public Characters(int id_character, String type, String character_class, String name, String categories, String superAttack, String ultraSuperAttack, String rarety, String passive, byte[] visual) {
        Id_character = id_character;
        Type = type;
        Character_class = character_class;
        Name = name;
        this.categories = categories;
        SuperAttack = superAttack;
        UltraSuperAttack = ultraSuperAttack;
        Rarety = rarety;
        Passive = passive;
        Visual = visual;
    }

    public int getId_character() {
        return Id_character;
    }

    public void setId_character(int id_character) {
        Id_character = id_character;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getCharacter_class() {
        return Character_class;
    }

    public void setCharacter_class(String character_class) {
        Character_class = character_class;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getSuperAttack() {
        return SuperAttack;
    }

    public void setSuperAttack(String superAttack) {
        SuperAttack = superAttack;
    }

    public String getUltraSuperAttack() {
        return UltraSuperAttack;
    }

    public void setUltraSuperAttack(String ultraSuperAttack) {
        UltraSuperAttack = ultraSuperAttack;
    }

    public String getRarety() {
        return Rarety;
    }

    public void setRarety(String rarety) {
        Rarety = rarety;
    }

    public String getPassive() {
        return Passive;
    }

    public void setPassive(String passive) {
        Passive = passive;
    }

    public byte[] getVisual() {
        return Visual;
    }

    public void setVisual(byte[] visual) {
        Visual = visual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Characters that = (Characters) o;
        return Objects.equals(Name, that.Name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Name);
    }

    @Override
    public String toString() {
        return "Characters{" +
                "Id_character=" + Id_character +
                ", Type='" + Type + '\'' +
                ", Character_class='" + Character_class + '\'' +
                ", Name='" + Name + '\'' +
                ", categories='" + categories + '\'' +
                ", SuperAttack='" + SuperAttack + '\'' +
                ", UltraSuperAttack='" + UltraSuperAttack + '\'' +
                ", Rarety='" + Rarety + '\'' +
                ", Passive='" + Passive + '\'' +
                ", Visual=" + Arrays.toString(Visual) +
                '}';
    }

}

