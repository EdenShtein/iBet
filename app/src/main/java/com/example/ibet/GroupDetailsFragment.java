package com.example.ibet;

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
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibet.model.Group.Group;
import com.example.ibet.model.Model;


public class GroupDetailsFragment extends Fragment {

    View view;
    TextView league;
    TableRow row;

    TextView group_name;

    String group_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_group_details, container, false);
        setHasOptionsMenu(true);



        league = view.findViewById(R.id.group_details_sub);
        group_name= view.findViewById(R.id.group_details_title);



        onInit();
        league.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_groupDetailsFragment_to_leagueDetailsFragment);
            }
        });

        row = view.findViewById(R.id.group_details_table_second);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.group_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.group_back:
                if(view != null) {
                    Navigation.findNavController(view).navigate(R.id.action_groupDetailsFragment_to_mainFeedFragment);
                }
                break;
            case R.id.menu_invite:
                if(view != null) {

                }
                break;
            default:

        }
        return super.onOptionsItemSelected(item);

    }

    public void onInit(){
        group_id = GroupDetailsFragmentArgs.fromBundle(getArguments()).getGroupID();
        Model.instance.getGroup(group_id, new Model.GroupListener() {
            @Override
            public void onComplete(boolean result, Group group) {
                group_name.setText(group.getName());
            }
        });
    }
}