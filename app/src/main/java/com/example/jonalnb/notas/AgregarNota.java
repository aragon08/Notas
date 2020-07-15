package com.example.jonalnb.notas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AgregarNota extends AppCompatActivity {
    @BindView(R.id.aNota) EditText aNota;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nota);
        ButterKnife.bind(this);
        user = getIntent().getStringExtra("user");
    }
    @OnClick(R.id.bAgregarN)
    public void agregar(){
        ParseObject notas = new ParseObject("Notas");
        notas.put("nota", aNota.getText().toString());
        notas.put("persona", user);
        notas.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                String message="";
                if(e == null) {
                    message = "Nota Guardada";
                    finish();
                }
                else
                    message = "Error al guardar la nota";
                Toast.makeText(AgregarNota.this,message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
