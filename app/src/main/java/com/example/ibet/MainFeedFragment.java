package com.example.ibet;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ibet.model.Group.Group;
import com.example.ibet.model.Group.GroupAdapter;
import com.example.ibet.model.Group.GroupViewModel;

import java.nio.file.attribute.GroupPrincipal;
import java.util.LinkedList;
import java.util.List;

public class MainFeedFragment extends Fragment {

   View view;
   SharedPreferences pref;
   SharedPreferences.Editor editor;
   public RecyclerView groupsList_rv;
   Button createGroup;
   GroupAdapter groupAdapter;
   private GroupViewModel groupViewModel;
   List<Group> groupList = new LinkedList<Group>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_feed, container, false);
        pref = getActivity().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        View decorView = getActivity().getWindow().getDecorView(); // Show the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);
        setHasOptionsMenu(true);

        groupsList_rv = view.findViewById(R.id.mainfeed_groupslist_rv);
        groupsList_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        groupsList_rv.setLayoutManager(layoutManager);
        groupAdapter = new GroupAdapter();

        groupViewModel = ViewModelProviders.of(getActivity()).get(GroupViewModel.class);

        Group group = new Group("1","Group 1", "1234");
        groupList.add(group);
        groupViewModel.insert(group);
        groupAdapter.setGroupsData(groupList);
        groupsList_rv.setAdapter(groupAdapter);

        createGroup = view.findViewById(R.id.mainfeed_create_group);

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
                    Navigation.findNavController(view).navigate(R.id.action_mainFeed_to_login);
                }
                break;
            case R.id.menu_my_profile:
                if(view != null) {
                    Navigation.findNavController(view).navigate(R.id.action_mainFeed_to_myProfile);
               }
                break;
            case R.id.menu_teams_result:
                if(view != null) {
                    Navigation.findNavController(view).navigate(R.id.action_mainFeed_to_teamsResult);
                }
                break;
            default:

        }
        return super.onOptionsItemSelected(item);

    }
}

