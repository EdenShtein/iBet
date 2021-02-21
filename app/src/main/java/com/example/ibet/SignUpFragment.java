package com.example.ibet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibet.model.Model;

public class SignUpFragment extends Fragment {
    View view;
    TextView signin;

    EditText email;
    EditText password;

    Button signup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_sign_up, container, false);


        email=view.findViewById(R.id.signup_email_input);
        password=view.findViewById(R.id.signup_password_input);
        signup=view.findViewById(R.id.signup_continue_btn);
        signin=view.findViewById(R.id.signin_link);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail=email.getText().toString();
                String userpassword=password.getText().toString();
                if (useremail.equals("") || userpassword.equals("")) {
                    Toast.makeText(getActivity(),"Please Enter Full Data",Toast.LENGTH_SHORT).show();
                }
                else {
                    Model.instance.signUp(useremail,userpassword);
                    Navigation.findNavController(view).navigate(R.id.action_signUp_to_login);
                }
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_signUp_to_login);
            }
        });
        return view;
    }
}