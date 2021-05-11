package com.example.ibet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.ibet.model.Model;
import com.example.ibet.model.Notifications.BetNotification;
import com.example.ibet.model.Notifications.MorningNotification;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    NavController navController;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Model.instance.setActivity(this);

        navController = Navigation.findNavController(this,R.id.fragment);

        Intent intent = getIntent();
        if(intent != null) {
            if(intent.getBooleanExtra("isLogin", false)) {
                userIsLogin();
            }
        }
        morningNotification();
        betNotification();

    }

    public void userIsLogin() {
        navController.navigate(R.id.action_login_to_mainFreed);
    }

    public void morningNotification(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,8);
        calendar.set(Calendar.MINUTE,00);
        calendar.set(Calendar.SECOND,00);

        Intent i = new Intent(getApplicationContext(), MorningNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,i,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    public void betNotification(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,11);
        calendar.set(Calendar.MINUTE,00);
        calendar.set(Calendar.SECOND,00);

        Intent i = new Intent(getApplicationContext(), BetNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,i,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,pendingIntent);
    }

}