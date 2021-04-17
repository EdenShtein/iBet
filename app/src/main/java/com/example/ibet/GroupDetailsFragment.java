package com.example.ibet;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ibet.model.Group.Group;
import com.example.ibet.model.Group.GroupViewModel;
import com.example.ibet.model.Match.Match;
import com.example.ibet.model.Match.MatchAdapter;
import com.example.ibet.model.Match.MatchViewModel;
import com.example.ibet.model.Model;
import com.example.ibet.model.User.User;
import com.example.ibet.model.User.UserAdapter;
import com.example.ibet.model.User.UserViewModel;

import java.util.ArrayList;
import java.util.List;


public class GroupDetailsFragment extends Fragment {

    View view;
    TextView league;
    Button upcoming_matches;

    TextView group_name;

    String group_id;
    Group currentGroup;

    public RecyclerView usersList_rv;
    UserAdapter userAdapter;
    private GroupViewModel groupViewModel;
    List<User> usersList = new ArrayList<User>();
    private UserViewModel userViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_group_details, container, false);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        usersList_rv = view.findViewById(R.id.group_details_rv);
        usersList_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        usersList_rv.setLayoutManager(layoutManager);
        userAdapter = new UserAdapter();

        groupViewModel = ViewModelProviders.of(getActivity()).get(GroupViewModel.class);
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        league = view.findViewById(R.id.group_details_sub);
        group_name= view.findViewById(R.id.group_details_title);
        upcoming_matches= view.findViewById(R.id.group_details_upcoming);

        onInit();

        userViewModel.getAllUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userAdapter.setUsersData(users);
                usersList_rv.setAdapter(userAdapter);
            }
        });

        league.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_groupDetailsFragment_to_leagueDetailsFragment);
            }
        });

        upcoming_matches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupDetailsFragmentDirections.ActionGroupDetailsToUpcomingMatches action = GroupDetailsFragmentDirections.actionGroupDetailsToUpcomingMatches(group_id);
                Navigation.findNavController(view).navigate(action);
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
            case R.id.group_invite:
                if(view != null) {
                    Model.instance.shareCode(currentGroup, new Model.GroupListener() {
                        @Override
                        public void onComplete(boolean result, Group group) {
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, group.getShareCode());
                            sendIntent.setType("text/plain");

                            Intent shareIntent = Intent.createChooser(sendIntent, null);
                            startActivity(shareIntent);
                        }
                    });
                }
                break;
            default:

        }
        return super.onOptionsItemSelected(item);

    }

    public void onInit(){
        group_id = GroupDetailsFragmentArgs.fromBundle(getArguments()).getGroupID();
        group_name.setText(GroupDetailsFragmentArgs.fromBundle(getArguments()).getGroupName());
        Model.instance.getGroupData(group_id, new Model.GroupListener() {
            @Override
            public void onComplete(boolean result, Group group) {
                currentGroup = group;
            }
        });

        Model.instance.getGroupUsers(group_id,new Model.UserListListener() {
            @Override
            public void onComplete(List<String> users) {
                for (String id : users){
                    User user = new User(id);
                    usersList.add(user);
                }
                for (User user : usersList) {
                    userViewModel.insert(user);
                }
            }
        });
    }
}