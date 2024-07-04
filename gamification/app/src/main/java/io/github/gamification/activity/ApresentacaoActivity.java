package io.github.gamification.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import io.github.gamification.R;
import io.github.gamification.config.RetrofitInstance;
import io.github.gamification.config.UsuarioLogado;
import io.github.gamification.model.Usuario;
import io.github.gamification.service.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApresentacaoActivity extends AppCompatActivity {

    private Button btContinuar;
    private List<String> mensagens = Arrays.asList(
            "Sou um aluno iniciante no curso de Análise e Desenvolvimento de Sistemas, preciso da " +
                    "sua ajuda para aprender os conceitos principais da programação!",
            "Durante seu uso do Gamificando, você terá a oportunidade de responder algumas questões sobre " +
                    "programação ao interagir com os alunos e professores do nosso curso. Com isso, você liberará insígnias, aumentará sua pontuação e consequentemente subirá no ranking dos usuários.",
            "Vou te mostrar alguns pontos importantes aqui do Gamificando, por exemplo, a tela inicial " +
                    "de status: Aqui você pode ver sua pontuação, ter acesso as suas insígnias conquistadas e ao ranking de pontuação dos usuários do App.",
            "Este é o mapa da universidade. Aqui você poderá selecionar quaisquer dos pontos em vermelho para interagir com algum dos personagens. Este personagem" +
                    "te apresentará um conteúdo relevante de programação e algumas questões para que você possa praticar.",
            "Espero que seu uso deste aplicativo seja bastante proveitoso, boa sorte!"
    );
    private int indexMsg = 0;

    private TextView textoApresentacao;
    private ImageView imagemTela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apresentacao);
        btContinuar = findViewById(R.id.btContinuar);
        textoApresentacao = findViewById(R.id.textoApresentacao);
        imagemTela = findViewById(R.id.imageView2);

        btContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (indexMsg < mensagens.size()){
                    textoApresentacao.setText(mensagens.get(indexMsg));

                    if (indexMsg == 2){
                        mudaImagem("img_status");
                    }else if (indexMsg == 3){
                        mudaImagem("img_mapa");
                    }else if (indexMsg == 4){
                        mudaImagem("student2");
                    }
                    indexMsg++;
                    return;
                }

                UsuarioLogado usuarioLogado = (UsuarioLogado)getApplicationContext();
                usuarioLogado.getUsuario().setVisualizouPrimeiraPagina(true);
                Call<Usuario> cadastro = RetrofitInstance.getRetrofitInstance()
                        .create(UsuarioService.class).cadastro(usuarioLogado.getUsuario());
                cadastro.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if (response!= null){
                            Intent intent = new Intent(ApresentacaoActivity.this, MenuActivity.class);
                            startActivity(intent);
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

    private void mudaImagem(String nomeImg){
        int imgId = getResources().getIdentifier(getApplicationContext().getPackageName()
                +":drawable/" + nomeImg, null, null);
        imagemTela.setImageResource(imgId);
    }
}