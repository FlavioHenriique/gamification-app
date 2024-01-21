package io.github.gamification.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.github.gamification.R;
import io.github.gamification.config.RetrofitInstance;
import io.github.gamification.model.Usuario;
import io.github.gamification.service.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends AppCompatActivity {

    private Button btCadastrar;
    private EditText edtNome;
    private EditText edtUsuario;
    private EditText edtSenha;
    private EditText edtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtNome = findViewById(R.id.edtNome);
        edtUsuario = findViewById(R.id.edtUsuario);
        edtSenha = findViewById(R.id.edtSenha);
        edtEmail = findViewById(R.id.edtEmail);
        btCadastrar = findViewById(R.id.btCadastro);

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario = new Usuario();
                usuario.setEmail(edtEmail.getText().toString());
                usuario.setNome(edtNome.getText().toString());
                usuario.setSenha(edtSenha.getText().toString());
                usuario.setUsuario(edtUsuario.getText().toString());

                if ("".equals(edtEmail.getText().toString()) ||
                        "".equals(edtNome.getText().toString()) ||
                        "".equals(edtSenha.getText().toString()) ||
                        "".equals(edtUsuario.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Call<Usuario> cadastro = RetrofitInstance.getRetrofitInstance()
                        .create(UsuarioService.class).cadastro(usuario);
                cadastro.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if (response != null){
                            Toast.makeText(getApplicationContext(), "Usuário cadastrado",
                                    Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
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