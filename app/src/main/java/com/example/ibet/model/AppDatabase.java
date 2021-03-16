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

@Database(entities = {Group.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract GroupDao groupDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app_database").fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }

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

        private PopulateDbAsyncTask(AppDatabase database) {
            groupDao = database.groupDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            groupDao.Insert(new Group("1", "First Group", "1234"));
            return null;
        }
    }
}
