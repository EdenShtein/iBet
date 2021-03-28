package com.example.ibet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ibet.model.Model;


public class ForgetPassFragment extends Fragment {

    View view;

    Button cancel;
    Button reset;
    Button send;

    EditText email;
    EditText token;
    EditText newpass;
    EditText renewpass;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_forget_pass, container, false);

        cancel = view.findViewById(R.id.forgetpass_cancel_btn);
        email = view.findViewById(R.id.forgot_email_input);
        reset = view.findViewById(R.id.forgetpass_reset_btn);
        send = view.findViewById(R.id.forgetpass_send_btn);
        token = view.findViewById(R.id.forgot_token_input);
        newpass = view.findViewById(R.id.forgot_newpass_input);
        renewpass = view.findViewById(R.id.forgot_renewpass_input);

        token.setVisibility(View.INVISIBLE);
        token.setEnabled(false);
        newpass.setVisibility(View.INVISIBLE);
        newpass.setEnabled(false);
        renewpass.setVisibility(View.INVISIBLE);
        renewpass.setEnabled(false);
        reset.setVisibility(View.INVISIBLE);
        reset.setEnabled(false);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = email.getText().toString();

                if (useremail.equals("")) {
                    Toast.makeText(getActivity(), "You must enter Email", Toast.LENGTH_SHORT).show();
                }
                if(!isValidEmail(useremail)){
                    Toast.makeText(getActivity(), "Please Enter Validate Email", Toast.LENGTH_SHORT).show();
                }else {
                    token.setVisibility(View.VISIBLE);
                    token.setEnabled(true);
                    newpass.setVisibility(View.VISIBLE);
                    newpass.setEnabled(true);
                    renewpass.setVisibility(View.VISIBLE);
                    renewpass.setEnabled(true);
                    send.setVisibility(View.INVISIBLE);
                    send.setEnabled(false);
                    reset.setVisibility(View.VISIBLE);
                    reset.setEnabled(true);
                    Model.instance.emailToken(useremail, new Model.SuccessListener() {
                        @Override
                        public void onComplete(boolean result) {
                            if (result) {
                                Toast.makeText(getActivity(), "Email Sent Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Failed To Send Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }});


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userToken = token.getText().toString();
                String userPass = newpass.getText().toString();
                String reNewPass = renewpass.getText().toString();

                if (userToken.equals("") || userPass.equals("") || reNewPass.equals(" ")) {
                    Toast.makeText(getActivity(),"You Must Enter Full Data",Toast.LENGTH_SHORT).show();
                }
                if(userPass.length()<8) {
                    Toast.makeText(getActivity(),"Password length must be minimum 8 characters",Toast.LENGTH_SHORT).show();
                }

                if(!(userPass.equals(reNewPass))) {
                    Toast.makeText(getActivity(),"Password are not the same",Toast.LENGTH_SHORT).show();
                }
                else {
                    token.setVisibility(View.VISIBLE);
                    token.setEnabled(true);
                    newpass.setVisibility(View.VISIBLE);
                    newpass.setEnabled(true);
                    renewpass.setVisibility(View.VISIBLE);
                    renewpass.setEnabled(true);
                    send.setVisibility(View.INVISIBLE);
                    send.setEnabled(false);
                    Model.instance.resetPassword(userToken, userPass, new Model.SuccessListener() {
                        @Override
                        public void onComplete(boolean result) {
                            if(result) {
                                Navigation.findNavController(view).navigate(R.id.action_forgetPass_to_login);
                            }
                            else{
                                Toast.makeText(getActivity(), "Failed To Send Token", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

        return view;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}