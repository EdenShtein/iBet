package com.example.ibet.model;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.ibet.model.Group.Group;
import com.example.ibet.model.Group.GroupDao;
import com.example.ibet.model.Match.Match;
import com.example.ibet.model.Match.MatchDao;
import com.example.ibet.model.Team.Team;
import com.example.ibet.model.Team.TeamDao;
import com.example.ibet.model.User.User;
import com.example.ibet.model.User.UserDao;
import com.example.ibet.model.User.UserLog;

@Database(entities = {Group.class, Team.class, Match.class, User.class, UserLog.class}, version = 21, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    abstract UserOnlyDao getUserOnlyDao();

    private static volatile User currentUser;

    public abstract GroupDao groupDao();
    public abstract TeamDao teamDao();
    public abstract MatchDao matchDao();
    public abstract UserDao userDao();

    public static synchronized AppDatabase getInstance(Context context, User user) {
        if (user == null) {
            throw  new RuntimeException("Attempt to open Invalid - CANNOT continue");
        }
        if ( currentUser == null || (currentUser.getId() != user.getId())) {
            if (instance != null) {
                if (instance.isOpen()) {
                    instance.close();
                }
                instance = null;
            }
        }
        if (instance == null) {
            instance = Room.databaseBuilder(context,AppDatabase.class,user.getUserName()+ "_log")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
       /* if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app_database").fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }*/

        return instance;
    }

    // Fill the DB with data.
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){

        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private GroupDao groupDao;
        private TeamDao teamDao;
        private MatchDao matchDao;
        private UserDao userDao;

        private PopulateDbAsyncTask(AppDatabase database) {
            groupDao = database.groupDao();
            teamDao = database.teamDao();
            matchDao = database.matchDao();
            userDao = database.userDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }
}
