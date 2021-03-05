package com.example.ibet;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainFreedFragment extends Fragment {

    View view;
   SharedPreferences pref;
   SharedPreferences.Editor editor;

   Button createGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_freed, container, false);
        pref = getActivity().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        View decorView = getActivity().getWindow().getDecorView(); // Show the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);


        createGroup = view.findViewById(R.id.mainfeed_create_group);

        setHasOptionsMenu(true);

        String token = pref.getString("token",null);

        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainFreed_to_createGroup);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sign_out:
                if(view != null) {
                    pref.edit().remove("token").commit();
                    Navigation.findNavController(view).navigate(R.id.action_mainFreed_to_login);
                }
                break;
            case R.id.menu_my_profile:
                if(view != null) {
                    Toast.makeText(getActivity(),"blalbla",Toast.LENGTH_LONG).show();
               }
                break;
            default:

        }
        return super.onOptionsItemSelected(item);

    }
}

