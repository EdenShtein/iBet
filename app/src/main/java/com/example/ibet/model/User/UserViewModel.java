package com.example.ibet.model.User;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.ibet.model.AppRepository;
import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private AppRepository repository;
    private LiveData<List<User>> usersList;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        usersList =repository.getAllUsers();
    }

    public void insert (User user){
        repository.insert(user);
    }

    public void update(User user){
        repository.update(user);
    }

    public void delete(User user){
        repository.delete(user);
    }

    public LiveData<List<User>> getAllUsers()
    {
        return usersList;
    }
}
