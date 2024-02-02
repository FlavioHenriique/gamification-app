package io.github.gamification.ui.status;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.w3c.dom.Text;

import java.util.stream.Collectors;

import io.github.gamification.activity.InsigniaActivity;
import io.github.gamification.activity.RankingActivity;
import io.github.gamification.config.UsuarioLogado;
import io.github.gamification.databinding.FragmentHomeBinding;
import io.github.gamification.model.Usuario;

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

        int qtdInsignias = usuario.getInsigniasConquistadas().size();
        int qtdConquistadas = usuario.getInsigniasConquistadas()
                .stream()
                .filter(i-> i.isConquistada())
                        .collect(Collectors.toList()).size();

        progressBar.setProgress((qtdConquistadas * 100)/qtdInsignias);
        tvInsignias.setText("Insígnias (" + qtdConquistadas + "/" + qtdInsignias + ")");
        tvPontuacao.setText(usuario.getPontuacao() + "");
        tvPosicao.setText(usuario.getPosicaoRanking() + "");
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
}