package com.example.ibet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
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
    EditText repassword;

    Button signup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_sign_up, container, false);

        email=view.findViewById(R.id.signup_email_input);
        password=view.findViewById(R.id.signup_pass_input);
        repassword = view.findViewById(R.id.signup_repass_input);
        signup=view.findViewById(R.id.signup_continue_btn);
        signin=view.findViewById(R.id.signin_link);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail=email.getText().toString();
                String userpassword=password.getText().toString();
                String rpassword = repassword.getText().toString();

                if (useremail.equals("") || userpassword.equals("")) {
                    Toast.makeText(getActivity(),"Please Enter Full Data",Toast.LENGTH_SHORT).show();
                }

                if(userpassword.length()<8) {
                    Toast.makeText(getActivity(),"Password length must be minimum 8 characters",Toast.LENGTH_SHORT).show();
                }

                if(!(userpassword.equals(rpassword))) {
                    Toast.makeText(getActivity(),"Password are not the same",Toast.LENGTH_SHORT).show();
                }
                else {
                    Model.instance.signUp(useremail, userpassword, new Model.SuccessListener() {
                        @Override
                        public void onComplete(boolean result) {
                            if(result) {
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "User Created Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Navigation.findNavController(view).navigate(R.id.action_signUp_to_login);
                            }
                            else{
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "Failed To Create User", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });

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