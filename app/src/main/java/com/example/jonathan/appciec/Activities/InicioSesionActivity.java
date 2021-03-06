package com.example.jonathan.appciec.Activities;

import android.content.Intent;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jonathan.appciec.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.regex.Pattern;


public class InicioSesionActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText txtCorreo,txtContrasenia;
    private Button btnRegistro;
    private FirebaseAuth myAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        myAuth = FirebaseAuth.getInstance();
        txtCorreo = findViewById(R.id.txtCorreo);
        txtContrasenia = findViewById(R.id.txtContrasenia);
        Button btnLogin = findViewById(R.id.btnLogin);
        btnRegistro = findViewById(R.id.btnRegistro);
        progressDialog = new ProgressDialog(this);
        btnLogin.setOnClickListener(this);
        btnRegistro.setOnClickListener(this);
    }
    private void loguearUsuario(){
        String correo = txtCorreo.getText().toString().trim();
        String contrasenia = txtContrasenia.getText().toString().trim();
        if (validarUsuario(correo) && validarContra(contrasenia)){
            progressDialog.setMessage("Realizando consulta");
            progressDialog.show();

            myAuth.signInWithEmailAndPassword(correo, contrasenia).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(InicioSesionActivity.this, "Bienvenido",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(InicioSesionActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(InicioSesionActivity.this, "Usuario existente",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(InicioSesionActivity.this, "Error al ingresar",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressDialog.dismiss();
                }
            });
        }
        else{
            Toast.makeText(InicioSesionActivity.this,"Error!!",Toast.LENGTH_LONG).show();
        }
    }
    private boolean validarUsuario(String correo){
        if (correo.length()==0) {
            Toast.makeText(InicioSesionActivity.this,"Se debe ingresar correo",Toast.LENGTH_LONG).show();
            return false;
        } else if (correo.length() > 0) {
            boolean emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+").matcher(correo).matches();
            if (!emailPattern) Toast.makeText(InicioSesionActivity.this, "Correo no válido", Toast.LENGTH_LONG).show();
            return emailPattern;

        }
        return false;
    }

    private boolean validarContra(String contra){
        if (contra.length() == 0 ) {
            Toast.makeText(InicioSesionActivity.this,"Contraseña vacía",Toast.LENGTH_LONG).show();
            return false;
        }else if (contra.length() < 6 ) {
            Toast.makeText(InicioSesionActivity.this,"Contraseña debe tener 6 dígitos o más",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==this.btnRegistro.getId()){
            Intent intent = new Intent(this, RegistroActivity.class);
            startActivity(intent);
        }
        else{
            loguearUsuario();
        }

    }
}
