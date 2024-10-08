package io.github.gamification.dialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import io.github.gamification.R;

public class DialogInsigniaFragment extends DialogFragment {

    private String descricao;
    private String titulo;
    private String imgName;
    private TextView insigniaDescricao;
    private ImageView insigniaImagem;
    private TextView insigniaTitulo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgName = getArguments().getString("IMG_NAME");
        descricao = getArguments().getString("DESCRICAO");
        titulo = getArguments().getString("TITULO");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_insignia, null);
        insigniaDescricao = view.findViewById(R.id.insigniaDescricao);
        insigniaTitulo = view.findViewById(R.id.insigniaTitulo);
        insigniaDescricao.setText(descricao);
        insigniaImagem = view.findViewById(R.id.insigniaImagem);
        insigniaTitulo.setText(titulo);

        int imgId = getResources().getIdentifier(getActivity().getApplicationContext().getPackageName()
                +":drawable/" + imgName , null, null);
        insigniaImagem.setImageResource(imgId);

        builder.setView(view)
                .setTitle("")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }

}
