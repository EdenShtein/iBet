package com.example.ibet.model;

import android.app.Activity;

public class Model {

    private Activity mActivity;
    public final static Model instance = new Model();
    Server server = new Server();

    public void signUp(String email,String password)
    {
        server.signUp(email,password);
    }
}
