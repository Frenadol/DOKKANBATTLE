package com.github.Frenadol.model.entity;


import java.util.HashSet;
import java.util.Objects;

public class Characters {
    private int id_character;
    private Type type;
    private Class character_class;
    private String name;
    private HashSet<String> categories;
    private String SuperAttack;
    private String UltraSuperAttack;
    private Rarety rarety;
    private String passive;
    public Characters(){

    }

    public Characters(int id_character, Type type, Class character_class, String name, HashSet<String> categories, String superAttack, String ultraSuperAttack, Rarety rarety, String passive) {
        this.id_character = id_character;
        this.type = type;
        this.character_class = character_class;
        this.name = name;
        this.categories = categories;
        SuperAttack = superAttack;
        UltraSuperAttack = ultraSuperAttack;
        this.rarety = rarety;
        this.passive = passive;
    }

    public int getId_character() {
        return id_character;
    }

    public void setId_character(int id_character) {
        this.id_character = id_character;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Class getCharacter_class() {
        return character_class;
    }

    public void setCharacter_class(Class character_class) {
        this.character_class = character_class;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<String> getCategories() {
        return categories;
    }

    public void setCategories(HashSet<String> categories) {
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

    public Rarety getRarety() {
        return rarety;
    }

    public void setRarety(Rarety rarety) {
        this.rarety = rarety;
    }

    public String getPassive() {
        return passive;
    }

    public void setPassive(String passive) {
        this.passive = passive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Characters that = (Characters) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Characters{" +
                "id_character=" + id_character +
                ", type=" + type +
                ", character_class=" + character_class +
                ", name='" + name + '\'' +
                ", categories=" + categories +
                ", SuperAttack='" + SuperAttack + '\'' +
                ", UltraSuperAttack='" + UltraSuperAttack + '\'' +
                ", rarety=" + rarety +
                ", passive='" + passive + '\'' +
                '}';
    }
}
