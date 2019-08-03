package com.example.jonathan.appciec;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ArticulosFragment extends Fragment implements View.OnClickListener {

    Button button_investigaciones;
    Button button_divulgaciones;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articulos, container,false);
        button_investigaciones = (Button) view.findViewById(R.id.button_Investigaciones);
        button_divulgaciones =  (Button) view.findViewById((R.id.button_Divulgaciones));
        button_investigaciones.setOnClickListener(this);
        button_divulgaciones.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_Investigaciones:
                verArticulosInvestigacion(v);
                break;
            case R.id.button_Divulgaciones:
                verArticulosDivulgacion(v);
                break;
        }
    }

    public void verArticulosInvestigacion(View view){
        Intent intent = new Intent(getActivity(), InvestigacionesActivity.class);
        startActivity(intent);
    }

    public void verArticulosDivulgacion(View view){
        Intent intent = new Intent(getActivity(), DivulgacionesActivity.class);
        startActivity(intent);

    }


}
