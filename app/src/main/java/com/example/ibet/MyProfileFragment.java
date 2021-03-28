package com.example.ibet;

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

import com.example.ibet.model.Group.Group;
import com.example.ibet.model.Group.GroupAdapter;
import com.example.ibet.model.Group.GroupViewModel;

import java.util.LinkedList;
import java.util.List;


public class MyProfileFragment extends Fragment {

    View view;
    RecyclerView groupsList_rv;
    GroupAdapter groupAdapter;
    GroupViewModel groupViewModel;
    List<Group> groupList = new LinkedList<Group>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        setHasOptionsMenu(true);

        groupsList_rv = view.findViewById(R.id.myprofile_history_rv);
        groupsList_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        groupsList_rv.setLayoutManager(layoutManager);
        groupAdapter = new GroupAdapter();

        groupViewModel = ViewModelProviders.of(getActivity()).get(GroupViewModel.class);

        Group group = new Group("2","Group 2", "1234");
        groupList.add(group);
        groupViewModel.insert(group);
        groupAdapter.setGroupsData(groupList);
        groupsList_rv.setAdapter(groupAdapter);


        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_rules:
                if(view != null) {
                    Navigation.findNavController(view).navigate(R.id.action_myProfileFragment_to_rulesFragment);
                }
                break;
            case R.id.back_btn:
                if(view != null) {
                    Navigation.findNavController(view).navigate(R.id.action_myProfileFragment_to_mainFeedFragment);
                }
                break;
            case R.id.profile_edit:
                if(view != null) {
                    Navigation.findNavController(view).navigate(R.id.action_myProfileFragment_to_editUserFragment);
                }
                break;
            default:

        }
        return super.onOptionsItemSelected(item);

    }
}