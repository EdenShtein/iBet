package com.example.ibet;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;


public class CreateGroupFragment extends Fragment {

    View view;
    EditText groupName;
    Spinner leagueDropDown;

    NumberPicker picker1;
    NumberPicker picker2;
    NumberPicker picker3;

    Button create;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_group, container, false);

        setHasOptionsMenu(true);

        groupName = view.findViewById(R.id.create_group_input_name);
        leagueDropDown = view.findViewById(R.id.create_group_league_dropdown);
        create = view.findViewById(R.id.create_group_create_btn);
        //back = view.findViewById(R.id.create_group_back_btn);
        picker1 = view.findViewById(R.id.create_group_picker1);
        picker2 = view.findViewById(R.id.create_group_picker2);
        picker3 = view.findViewById(R.id.create_group_picker3);

        picker1.setMinValue(1);
        picker1.setMaxValue(20);
        picker1.setValue(1);

        picker2.setMinValue(1);
        picker2.setMaxValue(20);
        picker2.setValue(3);

        picker3.setMinValue(1);
        picker3.setMaxValue(20);
        picker3.setValue(2);

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

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupName.getText().equals(" ") || groupName.getText().length()<2){
                    Toast.makeText(getActivity(), "Please Enter a valid group name!", Toast.LENGTH_SHORT).show();
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
}