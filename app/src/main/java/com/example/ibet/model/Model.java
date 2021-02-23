package com.example.ibet.model;

import android.app.Activity;

public class Model {

    private Activity mActivity;
    public final static Model instance = new Model();
    Server server = new Server();

    public void setActivity(Activity activity){
        this.mActivity = activity;
    }

    public void signUp(String email,String password,SuccessListener listener)
    {
        server.signUp(email,password,listener);
    }

    public interface SuccessListener{
        void onComplete(boolean result);
    }

    public void logIn(String email,String password, SuccessListener listener)
    {
        server.logIn(email,password,listener);
    }

    public void resetPass(String email,SuccessListener listener)
    {
        server.resetPass(email,listener);
    }
}
