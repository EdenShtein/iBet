package com.example.ibet.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ibet.model.Group.Group;
import com.example.ibet.model.Group.GroupViewModel;
import com.example.ibet.model.Match.Match;
import com.example.ibet.model.Match.MatchViewModel;
import com.example.ibet.model.Team.Team;
import com.example.ibet.model.Team.TeamViewModel;
import com.example.ibet.model.User.User;
import com.example.ibet.model.User.UserViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;

    private String mParam;
    private User mUser;

    public ViewModelFactory(Application application,String mParam, User user) {
        mApplication = application;
        mUser = user;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return null;
    }

   /* @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (mParam == "UserViewModel")
            return (T) new UserViewModel(mApplication, mUser);
        if (mParam == "GroupViewModel")
            return (T) new GroupViewModel(mApplication, mUser);
        if (mParam == "UserViewModel")
            return (T) new MatchViewModel(mApplication, mUser);
        if (mParam == "UserViewModel")
            return (T) new TeamViewModel(mApplication, mUser);
        else
            return null;
    }*/
}