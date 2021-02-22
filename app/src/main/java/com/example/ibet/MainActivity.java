package com.example.ibet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ibet.model.Model;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Model.instance.setActivity(this);
    }
}