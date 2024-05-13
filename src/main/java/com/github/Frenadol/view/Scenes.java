package com.github.Frenadol.view;

public enum Scenes {
    ROOT("view/layout.fxml"),
    INITIALMENU("view/initialMenu.fxml"),
    CHARACTER_LIST("view/characterList.fxml"),;


    private String url;
    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }

}
