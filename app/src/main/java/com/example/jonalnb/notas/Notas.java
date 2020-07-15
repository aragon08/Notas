package com.example.jonalnb.notas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Notas extends AppCompatActivity {
    @BindView(R.id.eNombre) EditText eNombre;
    @BindView(R.id.ePassword) EditText ePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.bLogin)
    public void logear(){
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setTitle("Cargando...");
        pDialog.setMessage("Cargando..");
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.show();
        ParseUser.logInInBackground(eNombre.getText().toString(), ePassword.getText().toString(), new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Intent intent = new Intent(Notas.this, Home.class);
                    intent.putExtra("user",user.getUsername());
                    startActivity(intent);
                } else {
                    Toast.makeText(Notas.this, "Usuario o password no valido", Toast.LENGTH_LONG).show();
                }
                eNombre.setText("");
                ePassword.setText("");
                pDialog.dismiss();
            }
        });
    }
    @OnClick(R.id.bRegistrar)
    public void registrar(){
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }
}
