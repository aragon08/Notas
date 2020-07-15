package com.example.jonalnb.notas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Registro extends AppCompatActivity {
    @BindView(R.id.eNombreR) EditText eNombre;
    @BindView(R.id.ePasswordR) EditText ePassword;
    @BindView(R.id.eCorreo) EditText eCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.bAtras)
    public void atras(){
        finish();
    }
    @OnClick(R.id.bRegistrarR)
    public void registrar(){
        ParseUser user = new ParseUser();
        String nombre = eNombre.getText().toString();
        String password = ePassword.getText().toString();
        String correo = eCorreo.getText().toString() ;
        if(nombre.trim().length()>0 && password.trim().length()>0 && correo.trim().length()>0) {
            user.setUsername(nombre);
            user.setPassword(password);
            user.setEmail(correo);
            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(Registro.this, "Cuenta creada correctamante.", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(Registro.this, "La cuenta es existente, cambie su nombre de usuario o su correo.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else
            Toast.makeText(Registro.this, "Algunos campos no tienen información.", Toast.LENGTH_LONG).show();
    }
}
