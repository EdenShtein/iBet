package com.example.ibet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.ibet.model.Model;

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

        pref = this.getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        String token = pref.getString("token","");
        if(pref.getString("token",null)!=null)
        {
            userIsLogin();
        }
    }
    public void userIsLogin() {
        navController.navigate(R.id.action_login_to_mainFreed);
    }
}