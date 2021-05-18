package com.example.ibet;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Patterns;
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
    EditText username;
    EditText password;
    EditText repassword;

    Button signup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_sign_up, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        email=view.findViewById(R.id.signup_email_input);
        username = view.findViewById(R.id.signup_username_input);
        password=view.findViewById(R.id.signup_pass_input);
        repassword = view.findViewById(R.id.signup_repass_input);
        signup=view.findViewById(R.id.signup_continue_btn);
        signin=view.findViewById(R.id.signin_link);

        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        email.setFilters(new InputFilter[] { filter });
        password.setFilters(new InputFilter[] { filter });
        repassword.setFilters(new InputFilter[] { filter });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail=email.getText().toString();
                String user_name = username.getText().toString();
                String userpassword=password.getText().toString();
                String rpassword = repassword.getText().toString();

                if (useremail.equals("") || userpassword.equals("") || user_name.equals("")) {
                    Toast.makeText(getActivity(),"Please Enter Full Data",Toast.LENGTH_SHORT).show();
                }
                if(!isValidEmail(useremail)){
                    Toast.makeText(getActivity(), "Please Enter Validate Email", Toast.LENGTH_SHORT).show();
                }
                if(userpassword.length()<8) {
                    Toast.makeText(getActivity(),"Password length must be minimum 8 characters",Toast.LENGTH_SHORT).show();
                }

                if(!(userpassword.equals(rpassword))) {
                    Toast.makeText(getActivity(),"Password are not the same",Toast.LENGTH_SHORT).show();
                }
                else {
                    Model.instance.signUp(useremail,user_name, userpassword, new Model.SuccessListener() {
                        @Override
                        public void onComplete(boolean result) {
                            if(result) {
                                Toast.makeText(getActivity(), "User Created Successfully", Toast.LENGTH_SHORT).show();
                                Navigation.findNavController(view).navigate(R.id.action_signUp_to_login);
                            }
                            else{
                                Toast.makeText(getActivity(), "Failed To Create User", Toast.LENGTH_SHORT).show();

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

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}