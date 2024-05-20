package com.github.Frenadol.view;

import  com.github.Frenadol.App;

import java.io.IOException;

public abstract class Controller {
    App app;
    /**
     * This method is used to set the App instance for this controller.
     * @param app The App instance to be set.
     */
    public void setApp(App app){
        this.app=app;
    }
    /**
     * This method is called when the view is opened.
     * It is an abstract method, so it must be implemented in any class that extends Controller.
     * @param input The input object to be used when the view is opened.
     * @throws IOException If an input or output exception occurred.
     */
    public abstract void onOpen(Object input) throws IOException;
    /**
     * This method is called when the view is closed.
     * It is an abstract method, so it must be implemented in any class that extends Controller.
     * @param output The output object to be used when the view is closed.
     */
    public abstract void onClose(Object output);
}
