package com.github.Frenadol.model.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Character_portal {
    private int Id_portal;
    private String Name_portal;
    private List<Characters> Featured_characters;
    private float Chance;
    private int Summon;
    private byte[] BannerImage;

    public Character_portal() {

    }

    public Character_portal(int id_portal, String name_portal, List<Characters> featured_characters, float chance, int summon, byte[] bannerImage) {
        Id_portal = id_portal;
        Name_portal = name_portal;
        Featured_characters = featured_characters;
        Chance = chance;
        Summon = summon;
        BannerImage = bannerImage;
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

    public List<Characters> getFeatured_characters() {
        return Featured_characters;
    }

    public void setFeatured_characters(List<Characters> featured_characters) {
        Featured_characters = featured_characters;
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

    public byte[] getBannerImage() {
        return BannerImage;
    }

    public void setBannerImage(byte[] bannerImage) {
        BannerImage = bannerImage;
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
                ", Featured_characters=" + Featured_characters +
                ", Chance=" + Chance +
                ", Summon=" + Summon +
                ", BannerImage=" + Arrays.toString(BannerImage) +
                '}';
    }
}

