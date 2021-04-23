package com.example.ibet.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.ibet.model.Group.Group;
import com.example.ibet.model.Group.GroupDao;
import com.example.ibet.model.Match.Match;
import com.example.ibet.model.Match.MatchDao;
import com.example.ibet.model.Team.Team;
import com.example.ibet.model.Team.TeamDao;
import com.example.ibet.model.User.User;
import com.example.ibet.model.User.UserDao;

import java.util.List;

public class AppRepository {

    private UserOnlyDao userOnlyDao;

    private GroupDao groupDao;
    private LiveData<List<Group>> groups;

    private TeamDao teamDao;
    private LiveData<List<Team>> teams;

    private MatchDao matchDao;
    private LiveData<List<Match>> matches;

    private UserDao userDao;
    private LiveData<List<User>> users;

    User currentUser = new User();

    public AppRepository(Application application,User user) {

        AppDatabase database = AppDatabase.getInstance(application,user);

        userOnlyDao = database.getUserOnlyDao();

        groupDao = database.groupDao();
        groups = groupDao.getAllGroups();

        teamDao = database.teamDao();
        teams = teamDao.getAllTeams();

        matchDao = database.matchDao();
        matches = matchDao.getAllMatches();

        userDao = database.userDao();
        users = userDao.getAllUsers();
    }
    public AppRepository(Application application) {

        AppDatabase database = AppDatabase.getInstance(application,currentUser);

        userOnlyDao = database.getUserOnlyDao();

        groupDao = database.groupDao();
        groups = groupDao.getAllGroups();

        teamDao = database.teamDao();
        teams = teamDao.getAllTeams();

        matchDao = database.matchDao();
        matches = matchDao.getAllMatches();

        userDao = database.userDao();
        users = userDao.getAllUsers();
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

    //-----------------Matches-------------------///

    public void insert(Match match)
    {
        new InsertMatchAsyncTask(matchDao).execute(match);
    }

    public void update(Match match)
    {
        new UpdateMatchAsyncTask(matchDao).execute(match);
    }

    public void delete(Match match)
    {
        new DeleteMatchAsyncTask(matchDao).execute(match);
    }

    public LiveData<List<Match>> getAllMatches()
    {
        return matches;
    }

    private static class InsertMatchAsyncTask extends AsyncTask<Match, Void, Void> {
        private MatchDao matchDao;
        private InsertMatchAsyncTask(MatchDao matchDao)
        {
            this.matchDao = matchDao;
        }

        @Override
        protected Void doInBackground(Match... matches) {
            matchDao.Insert(matches[0]);
            return null;
        }
    }

    private static class UpdateMatchAsyncTask extends AsyncTask<Match, Void, Void> {
        private MatchDao matchDao;
        private UpdateMatchAsyncTask(MatchDao matchDao)
        {
            this.matchDao = matchDao;
        }

        @Override
        protected Void doInBackground(Match... matches) {
            matchDao.Update(matches[0]);
            return null;
        }
    }

    private static class DeleteMatchAsyncTask extends AsyncTask<Match, Void, Void> {
        private MatchDao matchDao;
        private DeleteMatchAsyncTask(MatchDao matchDao)
        {
            this.matchDao = matchDao;
        }

        @Override
        protected Void doInBackground(Match... matches) {
            matchDao.delete(matches[0]);
            return null;
        }
    }

    //-----------------Users-------------------///

    public void insert(User user)
    {
        new InsertUserAsyncTask(userDao).execute(user);
    }

    public void update(User user)
    {
        new UpdateUserAsyncTask(userDao).execute(user);
    }

    public void delete(User user)
    {
        new DeleteUserAsyncTask(userDao).execute(user);
    }

    public LiveData<List<User>> getAllUsers()
    {
        return users;
    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;
        private InsertUserAsyncTask(UserDao userDao)
        {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.Insert(users[0]);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;
        private UpdateUserAsyncTask(UserDao userDao)
        {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.Update(users[0]);
            return null;
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;
        private DeleteUserAsyncTask(UserDao userDao)
        {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }

}
