package com.example.jonathan.appciec.Fragments;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.jonathan.appciec.Activities.BoletinesPoliticaActivity;
import com.example.jonathan.appciec.R;

public class BoletinesFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boletines, container,false);
        Button button_politica = view.findViewById(R.id.button_politica);
        Button button_otros = view.findViewById((R.id.button_otros));
        button_politica.setOnClickListener(this);
        button_otros.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_politica:
                verBoletines();
                break;
            case R.id.button_otros:
                verBoletines();
                break;
        }
    }

    private void verBoletines(){
        Intent intent = new Intent(getActivity(), BoletinesPoliticaActivity.class);
        startActivity(intent);
    }

    public void verArticulosDivulgacion(){
        Intent intent = new Intent(getActivity(), BoletinesPoliticaActivity.class);
        startActivity(intent);

    }


}
