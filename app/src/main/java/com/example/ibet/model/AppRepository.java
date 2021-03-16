package com.example.ibet.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.ibet.model.Group.Group;
import com.example.ibet.model.Group.GroupDao;

import java.util.List;

public class AppRepository {

    private GroupDao groupDao;
    private LiveData<List<Group>> groups;

    public AppRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        groupDao = database.groupDao();
        groups = groupDao.getAllGroups();

    }

    public void insert(Group group)
    {
        new InsertGroupAsyncTask(groupDao).execute(group);
    }

    public void update(Group group)
    {
        new UpdateGroupAsyncTask(groupDao).execute(group);
    }

    public void delete(Group group)
    {
        new DeleteGroupAsyncTask(groupDao).execute(group);
    }

    public LiveData<List<Group>> getAllGroups()
    {
        return groups;
    }

    //-----------------Groups-------------------///
    private static class InsertGroupAsyncTask extends AsyncTask<Group, Void, Void> {
        private GroupDao groupDao;
        private InsertGroupAsyncTask(GroupDao groupDao)
        {
            this.groupDao = groupDao;
        }

        @Override
        protected Void doInBackground(Group... groups) {
            groupDao.Insert(groups[0]);
            return null;
        }
    }

    private static class UpdateGroupAsyncTask extends AsyncTask<Group, Void, Void> {
        private GroupDao groupDao;
        private UpdateGroupAsyncTask(GroupDao groupDao)
        {
            this.groupDao = groupDao;
        }

        @Override
        protected Void doInBackground(Group... groups) {
            groupDao.Update(groups[0]);
            return null;
        }
    }

    private static class DeleteGroupAsyncTask extends AsyncTask<Group, Void, Void> {
        private GroupDao groupDao;
        private DeleteGroupAsyncTask(GroupDao groupDao)
        {
            this.groupDao = groupDao;
        }

        @Override
        protected Void doInBackground(Group... groups) {
            groupDao.delete(groups[0]);
            return null;
        }
    }

}
