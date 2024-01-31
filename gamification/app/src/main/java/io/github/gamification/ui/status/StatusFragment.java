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

import java.util.stream.Collectors;

import io.github.gamification.activity.InsigniaActivity;
import io.github.gamification.config.UsuarioLogado;
import io.github.gamification.databinding.FragmentHomeBinding;
import io.github.gamification.model.Usuario;

public class StatusFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Usuario usuario;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StatusViewModel homeViewModel =
                new ViewModelProvider(this).get(StatusViewModel.class);

        preencheUsuario();
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ProgressBar progressBar = binding.progressInsignias;
        int qtdInsignias = usuario.getInsigniasConquistadas().size();
        int qtdConquistadas = usuario.getInsigniasConquistadas()
                .stream()
                .filter(i-> i.isConquistada())
                        .collect(Collectors.toList()).size();

        progressBar.setProgress((qtdConquistadas * 100)/qtdInsignias);

        TextView tvInsignias = binding.tvInsignias;
        tvInsignias.setText("Insígnias (" + qtdConquistadas + "/" + qtdInsignias + ")");

        tvInsignias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FragmentManager fragmentManager = getFragmentManager();
                //fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, new InsigniaFragment()).commit();
                Intent intent = new Intent(getActivity(), InsigniaActivity.class);
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