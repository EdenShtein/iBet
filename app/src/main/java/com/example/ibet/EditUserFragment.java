package com.example.ibet;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ibet.model.Model;
import com.example.ibet.model.User.User;


public class EditUserFragment extends Fragment {

    View view;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    EditText email;
    EditText username;
    EditText password;
    EditText newPass;
    EditText confirmPass;

    Button updateMe;
    Button changePass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_user, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        email = view.findViewById(R.id.edituser_email_input);
        username = view.findViewById(R.id.edituser_username_input);
        updateMe = view.findViewById(R.id.edituser_confirm_btn);
        changePass = view.findViewById(R.id.edituser_confirm_btn2);
        password = view.findViewById(R.id.edituser_password_input);
        newPass = view.findViewById(R.id.edituser_newpass_input);
        confirmPass = view.findViewById(R.id.edituser_renewpass_input);

        setHasOptionsMenu(true);
        onInit();

        updateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail=email.getText().toString();
                String user_name = username.getText().toString();
                Model.instance.updateMe(useremail, user_name, new Model.SuccessListener() {
                    @Override
                    public void onComplete(boolean result) {
                        if(result){
                            Navigation.findNavController(view).navigate(R.id.action_editUserFragment_to_myProfileFragment);
                        }
                        else
                        {
                            Toast.makeText(getActivity(),"Something went wrong..",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userpass = password.getText().toString();
                String userNewPass = newPass.getText().toString();
                String userConfirmPass = confirmPass.getText().toString();
                if(userpass.equals(userNewPass))
                {
                    Toast.makeText(getActivity(),"You cant use the old password",Toast.LENGTH_SHORT).show();
                }
                if(userNewPass.equals(userConfirmPass)==false){
                    Toast.makeText(getActivity(),"The new passwords are not the same",Toast.LENGTH_SHORT).show();
                }
                if (userpass.equals("") || userNewPass.equals("") || userConfirmPass.equals("")) {
                    Toast.makeText(getActivity(),"You must enter values",Toast.LENGTH_SHORT).show();
                }
                else{
                    Model.instance.changePassword(userpass, userNewPass, new Model.SuccessListener() {
                        @Override
                        public void onComplete(boolean result) {
                            if(result){
                                Navigation.findNavController(view).navigate(R.id.action_editUserFragment_to_myProfileFragment);
                            }
                            else
                            {
                                Toast.makeText(getActivity(),"Something went wrong..",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.back_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back_btn:
                if(view != null) {
                    Navigation.findNavController(view).navigate(R.id.action_editUserFragment_to_myProfileFragment);
                }
                break;
            default:

        }
        return super.onOptionsItemSelected(item);

    }

    public void onInit(){
        Model.instance.getCurrentUserDetails(new Model.UserDetailsListener() {
            @Override
            public void onComplete(User user) {
                email.setText(user.getEmail());
                username.setText(user.getUserName());
            }
        });
    }
}