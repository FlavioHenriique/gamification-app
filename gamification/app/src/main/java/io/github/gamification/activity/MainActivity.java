package io.github.gamification.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.github.gamification.R;
import io.github.gamification.config.RetrofitInstance;
import io.github.gamification.config.UsuarioLogado;
import io.github.gamification.model.Usuario;
import io.github.gamification.service.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btLogin;
    private EditText edtEmail;
    private EditText edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btLogin = findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Usuario> login = RetrofitInstance.getRetrofitInstance()
                        .create(UsuarioService.class).login(edtEmail.getText().toString(),
                                edtSenha.getText().toString());
                login.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if (response != null){
                            UsuarioLogado usuarioLogado = (UsuarioLogado) getApplicationContext();
                            usuarioLogado.setUsuario(response.body());
                            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        System.out.println("erro: " + t.getMessage());
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}