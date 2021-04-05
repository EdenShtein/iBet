package com.example.ibet.model.Team;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ibet.model.AppRepository;

import java.util.List;

public class TeamViewModel extends AndroidViewModel {

    private AppRepository repository;
    private LiveData<List<Team>> teamsList;

    public TeamViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        teamsList =repository.getAllTeams();
    }

    public void insert (Team team){
        repository.insert(team);
    }

    public void update(Team team){
        repository.update(team);
    }

    public void delete(Team team){
        repository.delete(team);
    }

    public LiveData<List<Team>> getAllTeams()
    {
        return teamsList;
    }
}
