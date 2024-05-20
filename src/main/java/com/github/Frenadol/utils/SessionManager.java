package com.github.Frenadol.utils;

import com.github.Frenadol.model.entity.Users;

/**
 * Singleton class for managing user sessions.
 */
public class SessionManager {
    /** Singleton instance */
    private static final SessionManager instance = new SessionManager();

    /** Current user logged in */
    private Users currentUser;

    /** Private constructor to prevent instantiation from outside */
    private SessionManager() {}

    /**
     * Get the singleton instance of SessionManager.
     * @return The singleton instance of SessionManager.
     */
    public static SessionManager getInstance() {
        return instance;
    }

    /**
     * Set the user for the session.
     * This method is static and can be called without an instance.
     * However, it doesn't update the currentUser field of the singleton instance.
     * It seems like a placeholder method or a potential improvement point.
     * @param user The user to set for the session.
     */
    public static void setUser(Users user) {

    }

    /**
     * Set the current user for the session.
     * @param user The current user to set.
     */
    public void setCurrentUser(Users user) {
        this.currentUser = user;
    }

    /**
     * Get the current user for the session.
     * @return The current user for the session.
     */
    public Users getCurrentUser() {
        return currentUser;
    }
}
