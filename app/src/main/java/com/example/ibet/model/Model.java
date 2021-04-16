package com.example.ibet.model;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.ibet.model.Group.Group;
import com.example.ibet.model.Match.Match;
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

    public void updateMe(String email,String username,SuccessListener listener){
        pref = mActivity.getSharedPreferences("MyPref", 0);
        String token = pref.getString("token",null);
        server.updateMe(listener,mActivity,token,email,username);
    }

    public void changePassword(String password,String newPass,SuccessListener listener){
        pref = mActivity.getSharedPreferences("MyPref", 0);
        String token = pref.getString("token",null);
        server.changePassword(listener,mActivity,token,password,newPass);
    }

    public void getTeamData(TeamDataListener listener) {
        pref = mActivity.getSharedPreferences("MyPref", 0);
        String token = pref.getString("token",null);
        server.getTeamData(listener,mActivity,token);
    }

    public interface IdListener{
        public void onComplete(boolean result,String id);
    }

    public interface GroupListener{
        public void onComplete(boolean result, Group group);
    }

    public void createGroup(String groupName,int finalMatchWinner,int total,GroupListener listener){
        pref = mActivity.getSharedPreferences("MyPref", 0);
        String token = pref.getString("token",null);
        server.createGroup(listener,mActivity,token,groupName,finalMatchWinner,total);
    }

    public void getGroupData(String id,GroupListener listener){
        pref = mActivity.getSharedPreferences("MyPref", 0);
        String token = pref.getString("token",null);
        server.getGroupData(listener,mActivity,token,id);
    }

    public void shareCode(Group group,GroupListener listener){
        pref = mActivity.getSharedPreferences("MyPref", 0);
        String token = pref.getString("token",null);
        server.shareGroup(listener,mActivity,token,group);
    }

    public void joinGroup(String groupToken,SuccessListener listener){
        pref = mActivity.getSharedPreferences("MyPref", 0);
        String token = pref.getString("token",null);
        server.joinGroup(listener,mActivity,token,groupToken);
    }

    public interface MatchListener{
        public void onComplete(ArrayList<Match> upComingMatches);
    }

    public void getUpComingMatches(MatchListener listener){
        pref = mActivity.getSharedPreferences("MyPref", 0);
        String token = pref.getString("token",null);
        server.getUpComingGames(listener,mActivity,token);
    }





}
