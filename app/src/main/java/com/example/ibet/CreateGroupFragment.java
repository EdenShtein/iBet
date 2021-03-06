package com.example.ibet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;


public class CreateGroupFragment extends Fragment {

    View view;
    EditText groupName;
    Spinner leagueDropDown;

    NumberPicker picker1;

    Button create;
    Button back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_group, container, false);

        groupName = view.findViewById(R.id.create_group_input_name);
        leagueDropDown = view.findViewById(R.id.create_group_league_dropdown);
        create = view.findViewById(R.id.create_group_create_btn);
        back = view.findViewById(R.id.create_group_back_btn);
        picker1 = view.findViewById(R.id.create_group_picker1);

        picker1.setMinValue(1);
        picker1.setMaxValue(5);

        picker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d("picker1",String.valueOf(newVal));
            }
        });

        //create a list of items for the spinner.
        String[] leagues = new String[]{" ","NBA"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, leagues);
        //set the spinners adapter to the previously created one.
        leagueDropDown.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_createGroup_to_mainFreed);
            }
        });


        return view;
    }
}