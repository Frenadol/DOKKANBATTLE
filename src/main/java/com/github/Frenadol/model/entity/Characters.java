package com.github.Frenadol.model.entity;

import java.util.List;
import java.util.Objects;

public class Characters {
    private int Id_character;
    private Type Type;
    private Class Character_class;
    private String Name;
    private Category categories;
    private String SuperAttack;
    private String UltraSuperAttack;
    private Rarety Rarety;
    private String Passive;


    public Characters() {
    }

    public Characters(com.github.Frenadol.model.entity.Type type, Class character_class, String name, Category categories, String superAttack, String ultraSuperAttack, com.github.Frenadol.model.entity.Rarety rarety, String passive) {
        Type = type;
        Character_class = character_class;
        Name = name;
        this.categories = categories;
        SuperAttack = superAttack;
        UltraSuperAttack = ultraSuperAttack;
        Rarety = rarety;
        Passive = passive;
    }

    public int getId_character() {
        return Id_character;
    }

    public void setId_character(int id_character) {
        Id_character = id_character;
    }

    public com.github.Frenadol.model.entity.Type getType() {
        return Type;
    }

    public void setType(com.github.Frenadol.model.entity.Type type) {
        Type = type;
    }

    public Class getCharacter_class() {
        return Character_class;
    }

    public void setCharacter_class(Class character_class) {
        Character_class = character_class;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Category getCategories() {
        return categories;
    }

    public void setCategories(Category categories) {
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

    public com.github.Frenadol.model.entity.Rarety getRarety() {
        return Rarety;
    }

    public void setRarety(com.github.Frenadol.model.entity.Rarety rarety) {
        Rarety = rarety;
    }

    public String getPassive() {
        return Passive;
    }

    public void setPassive(String passive) {
        Passive = passive;
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
                ", Type=" + Type +
                ", Character_class=" + Character_class +
                ", Name='" + Name + '\'' +
                ", categories=" + categories +
                ", SuperAttack='" + SuperAttack + '\'' +
                ", UltraSuperAttack='" + UltraSuperAttack + '\'' +
                ", Rarety=" + Rarety +
                ", Passive='" + Passive + '\'' +
                '}';
    }
}


