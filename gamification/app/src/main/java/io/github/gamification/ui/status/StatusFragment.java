package io.github.gamification.ui.status;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.github.gamification.activity.InsigniaActivity;
import io.github.gamification.activity.InteracaoPersonagemActivity;
import io.github.gamification.activity.RankingActivity;
import io.github.gamification.config.RetrofitInstance;
import io.github.gamification.config.UsuarioLogado;
import io.github.gamification.databinding.FragmentHomeBinding;
import io.github.gamification.model.Personagem;
import io.github.gamification.model.Usuario;
import io.github.gamification.service.PersonagemService;
import io.github.gamification.util.Constants;
import io.github.gamification.util.ShowDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Usuario usuario;
    private TextView tvInsignias;
    private TextView tvPosicao;
    private TextView tvPontuacao;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StatusViewModel homeViewModel =
                new ViewModelProvider(this).get(StatusViewModel.class);

        preencheUsuario();
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        progressBar = binding.progressInsignias;
        tvInsignias = binding.tvInsignias;
        tvPontuacao = binding.tvPontuacaoValor;
        tvPosicao = binding.tvPosicaoValor;

        if (usuario != null){
            int qtdInsignias = usuario.getInsignias().size();
            int qtdConquistadas = usuario.getInsignias()
                    .stream()
                    .filter(i-> i.isConquistada())
                    .collect(Collectors.toList()).size();


            if (qtdInsignias > 0) {
                progressBar.setProgress((qtdConquistadas * 100)/qtdInsignias);
            }
            tvInsignias.setText("Insígnias (" + qtdConquistadas + "/" + qtdInsignias + ") - clique para ver todas");
            tvPontuacao.setText(usuario.getPontuacao() + "");
            tvPosicao.setText(usuario.getPosicaoRanking() + "º");
        }

        tvInsignias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InsigniaActivity.class);
                startActivity(intent);
            }
        });

        TextView tvPosicaoValor = binding.tvPosicaoValor;
        tvPosicaoValor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RankingActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }

    private void preencheUsuario() {
        UsuarioLogado usuarioLogado = (UsuarioLogado) getActivity().getApplicationContext();
        usuario = usuarioLogado.getUsuario();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mostraDialogProva();
    }

    private void mostraDialogProva(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Deseja focar em algum conteúdo específico?");


        final String[] listItems = new String[]{
                "Comandos de entrada e saída",
                "Condicionais",
                "Loops",
                "Subprograma",
                "Estruturas de dados",
                "Ponteiros"
        };
        final boolean[] checkedItems = new boolean[listItems.length];
        final List<String> selectedItems = Arrays.asList(listItems);


        builder.setMultiChoiceItems(listItems, checkedItems, (dialog, which, isChecked) -> {
            checkedItems[which] = isChecked;
            String currentItem = selectedItems.get(which);
        });

        builder.setCancelable(true);

        builder.setPositiveButton("SELECIONAR", (dialog, which) -> {
            List<Integer> personagensSelecionados = new ArrayList<>();
            for (int i = 0; i < checkedItems.length; i++) {
                if (checkedItems[i]) {
                    personagensSelecionados.add(Constants.getListaPersonagens().get(i));
                }
            }
            mostraTelaPersonagem(personagensSelecionados);
        });

        builder.create();

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void mostraTelaPersonagem(List<Integer> lista){
        Intent intent = new Intent(getActivity(), InteracaoPersonagemActivity.class);
        intent.putExtra("PERSONAGEM", lista.get(0));
        intent.putExtra("LISTA_PERSONAGENS", (Serializable) lista);
        startActivity(intent);
    }
}