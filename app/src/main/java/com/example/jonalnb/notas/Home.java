package com.example.jonalnb.notas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Home extends AppCompatActivity {
    String user;
    ArrayList<ParseObject> arr;
    @BindView(R.id.rvNotas) ListView rvNotas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        user =  getIntent().getStringExtra("user");
        verificarSesion();
    }
    public void verificarSesion(){
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null)
            login();
        llenar();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mLogout) {
            ParseUser.logOut();
            ParseUser currentUser = ParseUser.getCurrentUser();
            login();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void login(){
        finish();
    }
    public void llenar(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notas");
        query.whereEqualTo("persona", user);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                String message ="";
                arr = new ArrayList<>();
                if (e == null) {
                    for(ParseObject object: scoreList)
                        arr.add(object);
                    NoteAdapter a = new NoteAdapter(Home.this, arr);
                    a.setUser(user);
                    rvNotas.setAdapter(a);
                }
                else {
                    message = "Error: " + e.getMessage();
                    Toast.makeText(Home.this, message, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @OnClick(R.id.bNuevaN)
    public void nuevaNota(){
        Intent i = new Intent(this, AgregarNota.class);
        i.putExtra("user",user);
        startActivity(i);
    }
    @Override
    protected void onResume() {
        super.onResume();
        verificarSesion();
        llenar();
    }
}
