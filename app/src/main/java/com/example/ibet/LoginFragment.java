package com.example.ibet;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ibet.model.Model;


public class LoginFragment extends Fragment {
    View view;

    Button signUp;
    Button forgetPassword;
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

        signUp = view.findViewById(R.id.signin_signup_btn);
        email=view.findViewById(R.id.login_email_input);
        password=view.findViewById(R.id.login_pass_input);
        forgetPassword =view.findViewById(R.id.login_forgot_btn);
        signIn = view.findViewById(R.id.login_signin_btn);

        View decorView = getActivity().getWindow().getDecorView(); // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);


        pref = getActivity().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_login_to_signUp);
            }
        });


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();

                if (userEmail.equals("") || userPassword.equals("")) {
                    Toast.makeText(getActivity(), "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
                }

                if(!isValidEmail(userEmail)){
                    Toast.makeText(getActivity(), "Please Enter Validate Email", Toast.LENGTH_SHORT).show();
                } else {
                    Model.instance.logIn(userEmail, userPassword, new Model.LoginListener() {
                        @Override
                        public void onComplete(boolean result, String token) {
                            if(result) {
                                if(pref.getString("token",null) != null) {
                                    editor.remove("token");
                                } else {
                                    editor.putString("token",token).apply();
                                }

                                editor.commit();
                                Navigation.findNavController(view).navigate(R.id.action_login_to_mainFreed);
                                Toast.makeText(getActivity(), "Welcome to iBet", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Failed To Login", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_login_to_forgetPass);
            }
        });

        return view;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}