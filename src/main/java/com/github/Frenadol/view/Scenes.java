package com.github.Frenadol.view;

public enum Scenes {
    ROOT("view/layout.fxml"),
    MAIN("view/main.fxml"),
    ABOUT("view/about.fxml"),
    CHARACTER_LIST("view/characterList.fxml"),;

    private String url;
    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }

}
