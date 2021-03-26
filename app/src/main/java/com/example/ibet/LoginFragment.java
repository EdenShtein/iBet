package com.example.ibet;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ibet.model.Model;


public class LoginFragment extends Fragment {
    View view;

    Button signup;
    Button forgetPass;
    Button signIn;

    EditText email;
    EditText password;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        signup = view.findViewById(R.id.signin_signup_btn);
        email=view.findViewById(R.id.login_email_input);
        password=view.findViewById(R.id.login_pass_input);
        forgetPass=view.findViewById(R.id.login_forgot_btn);
        signIn = view.findViewById(R.id.login_signin_btn);

        pref = getActivity().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_login_to_signUp);
            }
        });


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = email.getText().toString();
                String userpassword = password.getText().toString();

                if (useremail.equals("") && userpassword.equals("")) {
                    Toast.makeText(getActivity(), "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
                } else {
                    Model.instance.logIn(useremail, userpassword, new Model.LoginListener() {

                        @Override
                        public void onComplete(boolean result, String token) {
                            if(result) {
                                editor.putString("token",token).apply();
                                Navigation.findNavController(view).navigate(R.id.action_login_to_mainFreed);
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "Welcome to iBet", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "Failed To Login", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_login_to_forgetPass);
            }
        });

        return view;
    }
}