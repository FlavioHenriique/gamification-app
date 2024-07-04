package io.github.gamification.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import java.io.Serializable;
import java.util.List;

import io.github.gamification.R;
import io.github.gamification.config.RetrofitInstance;
import io.github.gamification.config.UsuarioLogado;
import io.github.gamification.model.Alternativa;
import io.github.gamification.model.Dialogo;
import io.github.gamification.model.Personagem;
import io.github.gamification.model.RetornoInteracao;
import io.github.gamification.model.Usuario;
import io.github.gamification.model.request.SalvaQuestaoRequest;
import io.github.gamification.service.PersonagemService;
import io.github.gamification.util.ShowDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InteracaoPersonagemActivity extends AppCompatActivity {

    private Personagem personagem;
    private ImageView imgPersonagemEQuestao;
    private TextView tvDialogo;
    private RadioGroup radioGroupAlternativas;
    private Toolbar my_toolbar;

    private int dialogoAtual = -1;
    private Usuario usuario;

    private boolean recuperouQuestao;
    private List<Long> listaPersonagens = null;
    private int codPersonagem;

    private UsuarioLogado usuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_interacao_personagem);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recuperouQuestao = false;

        codPersonagem = (int) getIntent().getSerializableExtra("PERSONAGEM");
        if (getIntent().hasExtra("LISTA_PERSONAGENS")){
            listaPersonagens = (List<Long>) getIntent().getSerializableExtra("LISTA_PERSONAGENS");
        }

        usuarioLogado = (UsuarioLogado) getApplicationContext();
        usuario = usuarioLogado.getUsuario();

        my_toolbar = findViewById(R.id.my_toolbar);

        setSupportActionBar(my_toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        my_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        imgPersonagemEQuestao = findViewById(R.id.imgPersonagemEQuestao);
        tvDialogo = findViewById(R.id.tvDialogo);
        radioGroupAlternativas = findViewById(R.id.radioGroupAlternativas);



    }

    @Override
    protected void onResume() {
        super.onResume();
        consultaDadosPersonagem(codPersonagem);
    }

    private void proximoDialogo() throws Exception {
        if (personagem.getUltimaResposta() > 0 && !recuperouQuestao){
            int index = 0;
            for (int i = 0; i < personagem.getLinhasDialogo().size(); i ++){
                if (personagem.getLinhasDialogo().get(i).getIdQuestao() == personagem.getUltimaResposta()){
                    index = i;
                    break;
                }
            }
            if (index > 0) {
                dialogoAtual = index + 1;
                recuperouQuestao = true;
            }
        }else {
            dialogoAtual++;
        }
        if (personagem.getLinhasDialogo().size() > dialogoAtual){
            Dialogo dialogo = personagem.getLinhasDialogo().get(dialogoAtual);
            tvDialogo.setText(dialogo.getMensagem());

            radioGroupAlternativas.setOrientation(LinearLayout.VERTICAL);
            radioGroupAlternativas.removeAllViews();
            if (dialogo.getIdQuestao() > 0){
                int imgId = getResources().getIdentifier(getApplicationContext().getPackageName()
                        +":drawable/" + dialogo.getQuestao(), null, null);
                imgPersonagemEQuestao.setImageResource(imgId);
            }else{
                atualizaFotoPersonagem();
            }
            dialogo.getOpcoesResposta().forEach(opcao -> {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(opcao.getTexto());
                radioButton.setId(Integer.parseInt(opcao.getId()+ ""));
                radioButton.setTextSize(16);
                radioGroupAlternativas.addView(radioButton);
            });
            radioGroupAlternativas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int idQuestao = personagem.getLinhasDialogo().get(dialogoAtual).getIdQuestao();
                    Alternativa alternativa = personagem.getLinhasDialogo().get(dialogoAtual)
                            .getOpcoesResposta().get(checkedId);

                    if (idQuestao > 0){
                        SalvaQuestaoRequest request = new SalvaQuestaoRequest();
                        request.setCorreto(alternativa.isCorreto());
                        request.setIdPersonagem(personagem.getId());
                        request.setIdUsuario(usuario.getId());
                        request.setIdQuestao(idQuestao);
                        salvaRespostaDaQuestao(request);
                    }
                    try {
                        proximoDialogo();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
            });
        }else {
            if (listaPersonagens == null){
                finish();
            }else {
                listaPersonagens.remove(0);
                if (!listaPersonagens.isEmpty()){
                    Intent intent = new Intent(this, InteracaoPersonagemActivity.class);
                    intent.putExtra("PERSONAGEM", listaPersonagens.get(0));
                    intent.putExtra("LISTA_PERSONAGENS", (Serializable) listaPersonagens);
                    startActivity(intent);
                }
                finish();
            }


        }
    }

    private void salvaRespostaDaQuestao(SalvaQuestaoRequest request){
        ProgressDialog dialog= ShowDialog.showDialogIndeterminado(this, "Salvando questão...");
        dialog.show();
        Call<RetornoInteracao> salvaQuestao = RetrofitInstance.getRetrofitInstance()
                .create(PersonagemService.class).salvaRespostaQuestaoPersonagem(request);
        salvaQuestao.enqueue(new Callback<RetornoInteracao>() {
            @Override
            public void onResponse(Call<RetornoInteracao> call, Response<RetornoInteracao> response) {
                dialog.dismiss();
                if (response.body() != null){
                    if (response.body().getInsigniasLiberadas() != null && !response.body().getInsigniasLiberadas().isEmpty()){
                        response.body().getInsigniasLiberadas().forEach(i->
                                ShowDialog.showDialogInsignia(InteracaoPersonagemActivity.this,
                                        i.getId(), i.getNome(), i.getDescricao()));
                    }
                    if (response.body().getUsuario() != null){
                        usuarioLogado.setUsuario(response.body().getUsuario());
                    }
                }else{
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RetornoInteracao> call, Throwable t) {
                dialog.dismiss();
                System.out.println("erro: " + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void consultaDadosPersonagem(int idPersonagem){
        ProgressDialog progressDialog = ShowDialog.showDialogIndeterminado(this, "Acessando ambiente do personagem...");
        //progressDialog.show();
        Call<Personagem> find = RetrofitInstance.getRetrofitInstance()
                .create(PersonagemService.class).findById(idPersonagem, usuario.getId());
        find.enqueue(new Callback<Personagem>() {
            @Override
            public void onResponse(Call<Personagem> call, Response<Personagem> response) {
                progressDialog.dismiss();
                if (response != null){
                    try {
                        personagem = response.body();
                        my_toolbar.setTitle(personagem.getNome());
                        atualizaFotoPersonagem();

                        proximoDialogo();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<Personagem> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println("erro: " + t.getMessage());
                Toast.makeText(InteracaoPersonagemActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void atualizaFotoPersonagem(){
        int imgId = getResources().getIdentifier(getApplicationContext().getPackageName()
                +":drawable/" + personagem.getNome().toLowerCase(), null, null);
        imgPersonagemEQuestao.setImageResource(imgId);
    }
}