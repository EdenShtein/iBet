package com.example.ibet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ibet.model.Group.Group;
import com.example.ibet.model.Group.GroupViewModel;
import com.example.ibet.model.Match.Match;
import com.example.ibet.model.Model;


public class CreateGroupFragment extends Fragment {

    View view;
    EditText groupName;
    Spinner leagueDropDown;

    NumberPicker fullTimePointsPicker;
    NumberPicker totalGamePointsPicker;
    NumberPicker leagueWinnerPicker;

    int finalMatchWinner = 1;
    int total = 2;
    int leagueWinner = 30;

    Button create;

    private GroupViewModel groupViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_group, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        setHasOptionsMenu(true);

        groupName = view.findViewById(R.id.create_group_input_name);
        leagueDropDown = view.findViewById(R.id.create_group_league_dropdown);
        create = view.findViewById(R.id.create_group_create_btn);

        fullTimePointsPicker = view.findViewById(R.id.create_group_picker2);
        totalGamePointsPicker = view.findViewById(R.id.create_group_picker3);
        leagueWinnerPicker = view.findViewById(R.id.create_group_picker);

        setPointsPicker(fullTimePointsPicker, 1, 20, 1);
        setPointsPicker(totalGamePointsPicker, 1, 20, 2);
        setPointsPicker(leagueWinnerPicker, 30, 100, 30);

        //groupViewModel = ViewModelProviders.of(getActivity()).get(GroupViewModel.class);

        fullTimePointsPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                finalMatchWinner = newVal;
            }
        });

        totalGamePointsPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                total = newVal;
            }
        });

        leagueWinnerPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                leagueWinner = newVal;
            }
        });

        //create a list of items for the spinner.
        String[] leagues = new String[]{"NBA"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, leagues);
        //set the spinners adapter to the previously created one.
        leagueDropDown.setAdapter(adapter);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupName.getText().equals(" ") || groupName.getText().length() < 2) {
                    Toast.makeText(getActivity(), "Please Enter a valid group name!", Toast.LENGTH_SHORT).show();
                } else {
                    String name = groupName.getText().toString();
                    Model.instance.createGroup(name, finalMatchWinner, total, leagueWinner, new Model.GroupListener() {
                        @Override
                        public void onComplete(boolean result, Group group) {
                            if (result) {
                                //groupViewModel.insert(group);
                                Navigation.findNavController(view).navigate(R.id.action_createGroupFragment_to_mainFeedFragment);
                            } else {
                                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
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
                    Navigation.findNavController(view).navigate(R.id.action_createGroupFragment_to_mainFeedFragment);
                }
                break;
            default:

        }
        return super.onOptionsItemSelected(item);

    }

    // Sets default values for the point pickers
    public void setPointsPicker(NumberPicker pointPicker, int min, int max, int value) {
        try {
            pointPicker.setMinValue(min);
            pointPicker.setMaxValue(max);
            pointPicker.setValue(value);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}