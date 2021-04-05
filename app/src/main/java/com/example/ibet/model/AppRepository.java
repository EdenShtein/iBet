package com.example.ibet.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.ibet.model.Group.Group;
import com.example.ibet.model.Group.GroupDao;
import com.example.ibet.model.Team.Team;
import com.example.ibet.model.Team.TeamDao;

import java.util.List;

public class AppRepository {

    private GroupDao groupDao;
    private LiveData<List<Group>> groups;

    private TeamDao teamDao;
    private LiveData<List<Team>> teams;

    public AppRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        groupDao = database.groupDao();
        groups = groupDao.getAllGroups();

        teamDao = database.teamDao();
        teams = teamDao.getAllTeams();

    }

    //-----------------Groups-------------------///

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

    //-----------------Teams-------------------///

    public void insert(Team team)
    {
        new InsertTeamAsyncTask(teamDao).execute(team);
    }

    public void update(Team team)
    {
        new UpdateTeamAsyncTask(teamDao).execute(team);
    }

    public void delete(Team team)
    {
        new DeleteTeamAsyncTask(teamDao).execute(team);
    }

    public LiveData<List<Team>> getAllTeams()
    {
        return teams;
    }

    private static class InsertTeamAsyncTask extends AsyncTask<Team, Void, Void> {
        private TeamDao teamDao;
        private InsertTeamAsyncTask(TeamDao teamDao)
        {
            this.teamDao = teamDao;
        }

        @Override
        protected Void doInBackground(Team... teams) {
            teamDao.Insert(teams[0]);
            return null;
        }
    }

    private static class UpdateTeamAsyncTask extends AsyncTask<Team, Void, Void> {
        private TeamDao teamDao;
        private UpdateTeamAsyncTask(TeamDao teamDao)
        {
            this.teamDao = teamDao;
        }

        @Override
        protected Void doInBackground(Team... teams) {
            teamDao.Update(teams[0]);
            return null;
        }
    }

    private static class DeleteTeamAsyncTask extends AsyncTask<Team, Void, Void> {
        private TeamDao teamDao;
        private DeleteTeamAsyncTask(TeamDao teamDao)
        {
            this.teamDao = teamDao;
        }

        @Override
        protected Void doInBackground(Team... teams) {
            teamDao.delete(teams[0]);
            return null;
        }
    }

}
