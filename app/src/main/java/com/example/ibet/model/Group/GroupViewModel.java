package com.example.ibet.model.Group;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ibet.model.AppRepository;
import com.example.ibet.model.User.User;

import java.util.List;

public class GroupViewModel extends AndroidViewModel {

    private AppRepository repository;
    private LiveData<List<Group>> groupsList;

    /*public GroupViewModel(@NonNull Application application, User user) {
        super(application);
        repository = new AppRepository(application, user);
        groupsList =repository.getAllGroups();
    }*/
    public GroupViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        groupsList =repository.getAllGroups();
    }

    public void insert (Group group){
        repository.insert(group);
    }

    public void update(Group group){
        repository.update(group);
    }

    public void delete(Group group){
        repository.delete(group);
    }

    public LiveData<List<Group>> getAllGroups()
    {
        return groupsList;
    }
}
