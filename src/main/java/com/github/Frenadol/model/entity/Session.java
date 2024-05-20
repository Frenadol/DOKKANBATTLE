package com.github.Frenadol.model.entity;

public class Session {
    private static Session _instance;
    private static Users userLogged;
    private Session(){

    }
    /**
     * Gets the singleton instance of the Session class.
     * If the instance doesn't exist, it creates one and logs in the user.
     * @return The singleton instance of Session.
     */
    public static Session getInstance(){
        if (_instance==null){
            _instance = new Session();
            _instance.logIn(userLogged);
        }
        return _instance;
    }
    public void logIn(Users user){
        userLogged=user;
    }

    public Users getUserLogged(){
        return userLogged;
    }

    public void logOut(){
        userLogged=null;
    }
}