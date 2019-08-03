package com.example.jonathan.appciec.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jonathan.appciec.Models.Usuario;
import com.example.jonathan.appciec.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText txtCorreo,txtContrasenia,txtComprobacion;
    private DatabaseReference reff;
    private Usuario usuario;
    private FirebaseAuth myAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        myAuth = FirebaseAuth.getInstance();
        txtCorreo = findViewById(R.id.txtCorreo);
        txtContrasenia = findViewById(R.id.txtContrasenia);
        txtComprobacion = findViewById(R.id.txtComprobar);
        usuario = new Usuario();
        reff = FirebaseDatabase.getInstance().getReference().child("Usuario");
        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        Button btnLogin = findViewById(R.id.btnLogin);
        progressDialog = new ProgressDialog(this);
        btnRegistrar.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }
    private void registrarUsuario(){
        String correo = txtCorreo.getText().toString().trim();
        String contrasenia = txtContrasenia.getText().toString().trim();
        String comprobacion = txtComprobacion.getText().toString().trim();
        if (validarUsuario(correo,contrasenia,comprobacion)){
            usuario.setCorreo(correo);
            usuario.setContrasenia(contrasenia);
            usuario.setComprobarContra(comprobacion);
            reff.push().setValue(usuario);

            progressDialog.setMessage("Realizando registro");
            progressDialog.show();

            myAuth.createUserWithEmailAndPassword(correo, contrasenia).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegistroActivity.this, "Registro creado exitosamente",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(RegistroActivity.this, "Usuario existente",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegistroActivity.this, "Error al crear registro",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressDialog.dismiss();
                }
            });
        }
        else{
            Toast.makeText(RegistroActivity.this,"Contraseña y comprobación no coinciden",Toast.LENGTH_LONG).show();
        }
    }
    private boolean validarUsuario(String correo, String contra, String compro){
        if(correo.length()==0){
            Toast.makeText(RegistroActivity.this,"Se debe ingresar correo",Toast.LENGTH_LONG).show();
        }

        if (contra.equals(compro)){
            if (contra.length()<6){
                Toast.makeText(RegistroActivity.this,"Se debe ingresar contraseña con al menos 6 caracteres",Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }
        return false;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegistrar:
                registrarUsuario();
                break;
            case R.id.btnLogin:
                Intent intent = new Intent(this, InicioSesionActivity.class);
                startActivity(intent);
        }
    }
}
