package io.github.gamification.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.github.gamification.R;
import io.github.gamification.config.RetrofitInstance;
import io.github.gamification.config.UsuarioLogado;
import io.github.gamification.dialog.DialogInsigniaFragment;
import io.github.gamification.model.Usuario;
import io.github.gamification.service.UsuarioService;
import io.github.gamification.util.ShowDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btLogin;
    private Button btCadastre;
    private EditText edtEmail;
    private EditText edtSenha;
    private boolean teste = true;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btLogin = findViewById(R.id.btLogin);
        edtSenha = findViewById(R.id.edtSenha);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha.setText("flavio");
        edtEmail.setText("flavio@gmail.com");
        btCadastre = findViewById(R.id.btCadastro);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ShowDialog.showDialogIndeterminado(MainActivity.this, "Realizando login...");
                dialog.show();
                Call<Usuario> login = RetrofitInstance.getRetrofitInstance()
                        .create(UsuarioService.class).login(edtEmail.getText().toString(),
                                edtSenha.getText().toString());
                login.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if (dialog.isShowing())
                            dialog.dismiss();
                        if (response.body() != null){
                            UsuarioLogado usuarioLogado = (UsuarioLogado) getApplicationContext();
                            usuarioLogado.setUsuario(response.body());

                            Class activity = (response.body().isVisualizouPrimeiraPagina())
                                    ? MenuActivity.class : ApresentacaoActivity.class;
                            Intent intent = new Intent(MainActivity.this, activity);
                            startActivity(intent);
                        }
                        if (response.errorBody() != null){
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(getApplicationContext(), jObjError.getString("message"),
                                        Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        if (dialog.isShowing())
                            dialog.dismiss();
                        System.out.println("erro: " + t.getMessage());
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        btCadastre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

        ShowDialog.showDialogInsignia(this,   5,  "tasas", "desc");
    }
}