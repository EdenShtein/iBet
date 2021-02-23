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
import android.widget.Toast;

import com.example.ibet.model.Model;


public class ForgetPassFragment extends Fragment {

    View view;

    Button cancel;
    Button reset;
    EditText email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_forget_pass, container, false);

        cancel = view.findViewById(R.id.forgetpass_cancel_btn);
        email = view.findViewById(R.id.forgetpass_email_input);
        reset = view.findViewById(R.id.forgetpass_reset_btn);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = email.getText().toString();
                if (useremail.equals("")) {
                    Toast.makeText(getActivity(),"You must enter Email",Toast.LENGTH_SHORT).show();
                }
                else {
                    Model.instance.resetPass(useremail, new Model.SuccessListener() {
                        @Override
                        public void onComplete(boolean result) {
                            if (result) {
                                Navigation.findNavController(view).navigate(R.id.action_forgetPass_to_login);
                            } else {
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "Failed To Send Email", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

        return view;
    }
}