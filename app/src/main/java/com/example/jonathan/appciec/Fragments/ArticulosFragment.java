package com.example.jonathan.appciec.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.jonathan.appciec.Activities.DivulgacionesActivity;
import com.example.jonathan.appciec.Activities.InvestigacionesActivity;
import com.example.jonathan.appciec.R;

public class ArticulosFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articulos, container,false);
        Button button_investigaciones = view.findViewById(R.id.button_Investigaciones);
        Button button_divulgaciones = view.findViewById((R.id.button_Divulgaciones));
        button_investigaciones.setOnClickListener(this);
        button_divulgaciones.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_Investigaciones:
                verArticulosInvestigacion();
                break;
            case R.id.button_Divulgaciones:
                verArticulosDivulgacion();
                break;
        }
    }

    private void verArticulosInvestigacion(){
        Intent intent = new Intent(getActivity(), InvestigacionesActivity.class);
        startActivity(intent);
    }

    private void verArticulosDivulgacion(){
        Intent intent = new Intent(getActivity(), DivulgacionesActivity.class);
        startActivity(intent);

    }


}
