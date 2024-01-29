package io.github.gamification.ui.home;

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

import io.github.gamification.config.UsuarioLogado;
import io.github.gamification.databinding.FragmentHomeBinding;
import io.github.gamification.model.Usuario;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Usuario usuario;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

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