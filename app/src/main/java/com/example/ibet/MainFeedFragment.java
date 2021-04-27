package com.example.ibet;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibet.model.Group.Group;
import com.example.ibet.model.Group.GroupAdapter;
import com.example.ibet.model.Group.GroupViewModel;
import com.example.ibet.model.Model;
import com.example.ibet.model.User.User;
import com.example.ibet.model.User.UserViewModel;
import com.example.ibet.model.ViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.nio.file.attribute.GroupPrincipal;
import java.util.ArrayList;
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
   //LiveData<List<Group>> groupList;
   Dialog myDialog;
   String group_id;
    SwipeRefreshLayout swipeRefreshLayout;

    ArrayList<Group> groupList;

   BottomNavigationView bottomNav;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_feed, container, false);
        pref = getActivity().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("iBet");

        /*View decorView = getActivity().getWindow().getDecorView(); // Show the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);*/
        //setHasOptionsMenu(true);

        groupList = new ArrayList<>();

        bottomNav = view.findViewById(R.id.bottom_navigation_bar);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        groupsList_rv = view.findViewById(R.id.mainfeed_groupslist_rv);
        groupsList_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        groupsList_rv.setLayoutManager(layoutManager);
        groupAdapter = new GroupAdapter();

        User user = new User();

        //GroupViewModel groupViewModel = new ViewModelProvider(requireActivity()).get(GroupViewModel.class);
        //groupViewModel = ViewModelProviders.of(getActivity()).get(GroupViewModel.class);
        /*Group group = new Group("1","First Group","1234");
        groupViewModel.delete(group);*/

        Model.instance.getUsersGroup(new Model.GroupIdsListener() {
            @Override
            public void onComplete(ArrayList<String> groupIds) {
                for(String id: groupIds){
                    Model.instance.getGroupData(id, new Model.GroupListener() {
                        @Override
                        public void onComplete(boolean result, Group group) {
                            if(result){
                                groupList.add(group);
                                groupAdapter.setGroupsData(groupList);
                                groupsList_rv.setAdapter(groupAdapter);
                            }
                        }
                    });
                }
            }
        });

        swipeRefreshLayout = view.findViewById(R.id.mainfeed_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.design_default_color_primary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                ArrayList<Group> refreshedList = new ArrayList<>();
                Model.instance.getUsersGroup(new Model.GroupIdsListener() {
                    @Override
                    public void onComplete(ArrayList<String> groupIds) {
                        for(String id: groupIds){
                            Model.instance.getGroupData(id, new Model.GroupListener() {
                                @Override
                                public void onComplete(boolean result, Group group) {
                                    if(result){
                                        refreshedList.add(group);
                                        groupAdapter.setGroupsData(refreshedList);
                                        groupsList_rv.setAdapter(groupAdapter);
                                    }
                                }
                            });
                        }
                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });


//        groupViewModel.getAllGroups().observe(getViewLifecycleOwner(), new Observer<List<Group>>() {
//            @Override
//            public void onChanged(List<Group> groups) {
//                groupList.addAll(groups);
//                groupAdapter.setGroupsData(groupList);
//                groupsList_rv.setAdapter(groupAdapter);
//            }
//        });

        createGroup = view.findViewById(R.id.mainfeed_create_group);

        String token = pref.getString("token",null);

        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainFeedFragment_to_createGroupFragment);
            }
        });

        groupAdapter.setOnItemClickListener(new GroupAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Group group, View view) {
                MainFeedFragmentDirections.ActionMainFeedFragmentToGroupDetailsFragment action = MainFeedFragmentDirections.actionMainFeedFragmentToGroupDetailsFragment(group.getId(),group.getName());
                Navigation.findNavController(view).navigate(action);
            }
        });

        myDialog = new Dialog(view.getContext());


        return view;
    }

    public void ShowPopup(View v){

        EditText groupToken;
        ImageView closeBtn;
        Button confirmBtn;
        myDialog.setContentView(R.layout.fragment_invitation_pop_up);
        groupToken = myDialog.findViewById(R.id.dialog_edittext);
        closeBtn = myDialog.findViewById(R.id.pop_close_btn);
        confirmBtn = (Button) myDialog.findViewById(R.id.pop_confirm_btn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = groupToken.getText().toString();
                Model.instance.joinGroup(token, new Model.SuccessListener() {
                    @Override
                    public void onComplete(boolean result) {
                        if(result)
                        {
                            Toast.makeText(getActivity(),"New Group has been added",Toast.LENGTH_SHORT).show();
                            myDialog.dismiss();
                        }
                        else{
                            Toast.makeText(getActivity(),"Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        myDialog.show();
    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.main_menu,menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_sign_out:
//                if(view != null) {
//                    pref.edit().remove("token").commit();
//                    Navigation.findNavController(view).navigate(R.id.action_mainFeed_to_login);
//                }
//                break;
////            case R.id.menu_my_profile:
////                if(view != null) {
////                    Navigation.findNavController(view).navigate(R.id.action_mainFeed_to_myProfile);
////               }
////                break;
//            case R.id.menu_teams_result:
//                if(view != null) {
//                    Navigation.findNavController(view).navigate(R.id.action_mainFeed_to_teamsResult);
//                }
//                break;
////            case R.id.menu_invite:
////                if(view != null) {
////                    ShowPopup(view);
////                }
////                break;
//            default:
//
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_my_profile:
                    if (view != null) {
                        Navigation.findNavController(view).navigate(R.id.action_mainFeed_to_myProfile);
                    }
                    break;
                case R.id.nav_join:
                    if (view != null) {
                        ShowPopup(view);
                    }
                    break;
                case R.id.nav_main_feed:
                    if(view!=null){
                    }
                    break;
                case R.id.nav_dashboard:
                    if(view!=null){
                        Navigation.findNavController(view).navigate(R.id.action_mainFeed_to_dashboard);
                    }
                    break;

                default:

            }
            return true;
        }
    };
}

