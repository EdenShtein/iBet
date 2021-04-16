package com.example.ibet.model.Match;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.ibet.model.AppRepository;

import java.util.List;

public class MatchViewModel extends AndroidViewModel {

    private AppRepository repository;
    private LiveData<List<Match>> matchesList;

    public MatchViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        matchesList =repository.getAllMatches();
    }

    public void insert (Match match){
        repository.insert(match);
    }

    public void update(Match match){
        repository.update(match);
    }

    public void delete(Match match){
        repository.delete(match);
    }

    public LiveData<List<Match>> getAllMatches()
    {
        return matchesList;
    }
}
