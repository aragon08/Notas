package com.example.jonalnb.notas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import static android.R.attr.resource;

/**
 * Created by JonaLNB on 06/05/2017.
 */

public class NoteAdapter extends ArrayAdapter<ParseObject>{
    private Context context;
    private List<ParseObject> listNotes;
    private String user;
    private ParseObject objectSelected;

    public NoteAdapter(@NonNull Context context, List<ParseObject> listNotes) {
        super(context, 0, listNotes);
        this.context=context;
        this.listNotes=listNotes;
    }
    public void setUser(String user){
        this.user=user;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView tvNota;
        TextView tvFecha;
        ImageButton bEliminar;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.lista_nota, parent, false);
        }
        objectSelected = listNotes.get(position);
        tvNota = (TextView) convertView.findViewById(R.id.tvNota);
        tvFecha = (TextView) convertView.findViewById(R.id.tvFecha);
        bEliminar = (ImageButton) convertView.findViewById(R.id.bEliminar);
        bEliminar.setOnClickListener(listenerEliminar);
        bEliminar.setTag(objectSelected.getObjectId());
        tvNota.setText(objectSelected.getString("nota"));
        tvFecha.setText(objectSelected.getCreatedAt().toString());
        return convertView;
    }
    View.OnClickListener listenerEliminar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final View view = v;
            AlertDialog.Builder confirmacion = new AlertDialog.Builder(context);
            confirmacion.setTitle("Eliminar");
            confirmacion.setMessage("¿Estas seguro de eliminar la nota?");
            confirmacion.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                         eliminarNota(view.getTag().toString());
                }
            });
            confirmacion.setNegativeButton("No", null);
            confirmacion.show();
        }
    };
    public void eliminarNota(String id){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notas");
        query.getInBackground(id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if(e==null){
                    object.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null){
                                Intent i = new Intent(context, Home.class);
                                i.putExtra("user",user);
                                context.startActivity(i);
                            }
                        }
                    });
                }
            }
        });
    }
}
