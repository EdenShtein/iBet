package com.example.ibet;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ibet.model.Model;
import com.example.ibet.model.User.User;


public class EditUserFragment extends Fragment {

    View view;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_user, container, false);

        setHasOptionsMenu(true);
        onInit();

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

            }
        });
    }
}