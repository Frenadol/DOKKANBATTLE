package com.github.Frenadol.utils;

import com.github.Frenadol.model.entity.Users;

public class SessionManager {
    private static final SessionManager instance = new SessionManager();
    private Users currentUser;

    private SessionManager() {}

    public static SessionManager getInstance() {
        return instance;
    }

    public static void setUser(Users user) {
    }

    public void setCurrentUser(Users user) {
        this.currentUser = user;
    }

    public Users getCurrentUser() {
        return currentUser;
    }
}
