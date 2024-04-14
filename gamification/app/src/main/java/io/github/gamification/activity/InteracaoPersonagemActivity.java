package io.github.gamification.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import androidx.navigation.ui.NavigationUI;

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
        personagem = (Personagem) getIntent().getSerializableExtra("PERSONAGEM");
        UsuarioLogado usuarioLogado = (UsuarioLogado) getApplicationContext();
        usuario = usuarioLogado.getUsuario();

        my_toolbar = findViewById(R.id.my_toolbar);
        my_toolbar.setTitle(personagem.getNome());

        imgPersonagemEQuestao = findViewById(R.id.imgPersonagemEQuestao);
        tvDialogo = findViewById(R.id.tvDialogo);
        radioGroupAlternativas = findViewById(R.id.radioGroupAlternativas);

        int imgId = getResources().getIdentifier(getApplicationContext().getPackageName()
                +":drawable/" + personagem.getNome().toLowerCase(), null, null);
        imgPersonagemEQuestao.setImageResource(imgId);
        proximoDialogo();

    }

    private void proximoDialogo(){
        dialogoAtual++;
        if (personagem.getLinhasDialogo().size() >= dialogoAtual){
            Dialogo dialogo = personagem.getLinhasDialogo().get(dialogoAtual);
            tvDialogo.setText(dialogo.getMensagem());

            radioGroupAlternativas.setOrientation(LinearLayout.VERTICAL);
            radioGroupAlternativas.removeAllViews();
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
                    proximoDialogo();

                }
            });
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
                if (response != null){

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
}