package com.example.ibet.model;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.ibet.model.Team.Team;
import com.example.ibet.model.User.User;

import java.util.ArrayList;

public class Model {

    public Activity mActivity;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public final static Model instance = new Model(){};
    Server server = new Server();


    public void setActivity(Activity activity){
        this.mActivity = activity;
    }

    public Activity getActivity () {return this.mActivity;}

    public void signUp(String email,String username,String password,SuccessListener listener) {
        server.signUp(email,username,password,listener,mActivity);
    }

    public interface SuccessListener {
        void onComplete(boolean result);
    }

    public interface LoginListener{
        public void onComplete(boolean result,String token);
    }

    public void logIn(String email,String password, LoginListener listener) {
        server.logIn(email,password,listener,mActivity);
    }

    public void emailToken(String email,SuccessListener listener) {
        server.emailToken(email,listener,mActivity);
    }
    public void resetPassword(String token,String password,SuccessListener listener)
    {
        server.restPassword(token,password,listener,mActivity);
    }


    public interface TeamDataListener{
        public void onComplete(ArrayList<Team> teamData);
    }

    /*public void getAlgoResult(TeamDataListener listener) {
        server.getAlgoResult(listener);
    }*/
    public void getAlgoResults(TeamDataListener listener) {
        server.getAlgoResult(listener,mActivity);
    }

    public interface UserDetailsListener{
        public void onComplete(User user);
    }
    public void getCurrentUserDetails(UserDetailsListener listener){
        pref = mActivity.getSharedPreferences("MyPref", 0);
        String token = pref.getString("token",null);
        server.getCurrentUserDetails(listener,mActivity,token);
    }


}
