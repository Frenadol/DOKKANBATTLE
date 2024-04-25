package com.github.Frenadol.model.entity;


import java.util.HashSet;
import java.util.Objects;

public class Characters {
    Characters characters= new Characters();
    private int Id_character;
    private Type Type;
    private Class Character_class;
    private String Name;
    private HashSet<String> Categories;
    private String SuperAttack;
    private String UltraSuperAttack;
    private Rarety Rarety;
    private String Passive;
    public Characters(){

    }

    public Characters(int id_character, Type type, Class character_class, String name, HashSet<String> categories, String superAttack, String ultraSuperAttack, Rarety rarety, String passive) {
        this.Id_character = id_character;
        this.Type = type;
        this.Character_class = character_class;
        this.Name = name;
        this.Categories = new HashSet<>();
        SuperAttack = superAttack;
        UltraSuperAttack = ultraSuperAttack;
        this.Rarety = rarety;
        this.Passive = passive;
        addCategories("Saiyan");
        addCategories("Hybrids Saiyans");
        addCategories("Sworn enemies");
    }

    public int getId_character() {
        return Id_character;
    }

    public void setId_character(int id_character) {
        this.Id_character = id_character;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type type) {
        this.Type = type;
    }

    public Class getCharacter_class() {
        return Character_class;
    }

    public void setCharacter_class(Class character_class) {
        this.Character_class = character_class;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public HashSet<String> getCategories() {
        return Categories;
    }

    public void setCategories(HashSet<String> categories) {
        this.Categories = categories;
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
        return Rarety;
    }

    public void setRarety(Rarety rarety) {
        this.Rarety = rarety;
    }

    public String getPassive() {
        return Passive;
    }

    public void setPassive(String passive) {
        this.Passive = passive;
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
                "id_character=" + Id_character +
                ", type=" + Type +
                ", character_class=" + Character_class +
                ", name='" + Name + '\'' +
                ", categories=" + Categories +
                ", SuperAttack='" + SuperAttack + '\'' +
                ", UltraSuperAttack='" + UltraSuperAttack + '\'' +
                ", rarety=" + Rarety +
                ", passive='" + Passive + '\'' +
                '}';
    }

    public void addCategories(String categorie) {
        if (this.Categories == null) {
            this.Categories = new HashSet<>();
        }
        this.Categories.add(categorie);
    }

}
