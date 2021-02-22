package com.example.ibet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
                    Model.instance.logIn(useremail, userpassword, new Model.SuccessListener() {
                        @Override
                        public void onComplete(boolean result) {
                                if(result)
                                {
                                    Navigation.findNavController(view).navigate(R.id.action_login_to_mainFreed);
                                }
                                else {
                                    Toast.makeText(getActivity(), "Failed to login", Toast.LENGTH_SHORT).show();
                                }
                        }
                    });
                }
            }
        });


        return view;
    }
}