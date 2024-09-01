package io.github.gamification.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtEmail.getText().toString().contains("@")){
                    Toast.makeText(getApplicationContext(), "Email inválido!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
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
                        if (response.errorBody() != null) {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(getApplicationContext(), jObjError.get("message").toString(),
                                        Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }else{
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