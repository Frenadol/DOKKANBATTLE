package com.github.Frenadol.model.entity;

import java.util.List;
import java.util.Objects;

public class Character_portal {
    private int Id_portal;
    private String Name_portal;
    private List<Characters> Characterslist;
    private float Chance;
    private int Summon;

    public Character_portal(int id_portal, String name_portal, List<Characters> characterslist, float chance, int summon) {
        Id_portal = id_portal;
        Name_portal = name_portal;
        Characterslist = characterslist;
        Chance = chance;
        Summon = summon;
    }
    public Character_portal(){

    }

    public int getId_portal() {
        return Id_portal;
    }

    public void setId_portal(int id_portal) {
        Id_portal = id_portal;
    }

    public String getName_portal() {
        return Name_portal;
    }

    public void setName_portal(String name_portal) {
        Name_portal = name_portal;
    }

    public List<Characters> getCharacterslist() {
        return Characterslist;
    }

    public void setCharacterslist(List<Characters> characterslist) {
        Characterslist = characterslist;
    }

    public float getChance() {
        return Chance;
    }

    public void setChance(float chance) {
        Chance = chance;
    }

    public int getSummon() {
        return Summon;
    }

    public void setSummon(int summon) {
        Summon = summon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character_portal that = (Character_portal) o;
        return Id_portal == that.Id_portal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id_portal);
    }

    @Override
    public String toString() {
        return "Character_portal{" +
                "Id_portal=" + Id_portal +
                ", Name_portal='" + Name_portal + '\'' +
                ", Characterslist=" + Characterslist +
                ", Chance=" + Chance +
                ", Summon=" + Summon +
                '}';
    }
}
