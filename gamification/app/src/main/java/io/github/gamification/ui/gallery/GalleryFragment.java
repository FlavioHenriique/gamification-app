package io.github.gamification.ui.gallery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.github.gamification.activity.InteracaoPersonagemActivity;
import io.github.gamification.config.RetrofitInstance;
import io.github.gamification.config.UsuarioLogado;
import io.github.gamification.databinding.FragmentGalleryBinding;
import io.github.gamification.model.Personagem;
import io.github.gamification.model.Usuario;
import io.github.gamification.service.PersonagemService;
import io.github.gamification.service.UsuarioService;
import io.github.gamification.util.Constants;
import io.github.gamification.util.ShowDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryFragment extends Fragment {

    private ExtendedFloatingActionButton btFabiano;
    private ExtendedFloatingActionButton btJacqueline;
    private ExtendedFloatingActionButton btMarcela;
    private ExtendedFloatingActionButton btTulio;
    private ExtendedFloatingActionButton btLucas;
    private ExtendedFloatingActionButton btIa;

    private FragmentGalleryBinding binding;
    private Usuario usuario;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btFabiano = binding.fabProfFabiano;
        btJacqueline = binding.fabProfJacqueline;
        btLucas = binding.fabAlLucas;
        btMarcela = binding.fabAlMarcela;
        btTulio = binding.fabAlTulio;
        btIa = binding.fabIA;

        UsuarioLogado usuarioLogado = (UsuarioLogado) getActivity().getApplicationContext();
        usuario = usuarioLogado.getUsuario();

        btFabiano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultaDadosPersonagem(Constants.ID_PERSONAGEM_FABIANO);
            }
        });

        btJacqueline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultaDadosPersonagem(Constants.ID_PERSONAGEM_JACQUELINE);
            }
        });

        btLucas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultaDadosPersonagem(Constants.ID_PERSONAGEM_LUCAS);
            }
        });

        btMarcela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultaDadosPersonagem(Constants.ID_PERSONAGEM_MARCELA);
            }
        });
        btTulio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultaDadosPersonagem(Constants.ID_PERSONAGEM_TULIO);
            }
        });

        btIa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultaDadosPersonagem(Constants.ID_PERSONAGEM_IAN);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void consultaDadosPersonagem(int idPersonagem){
        Intent intent = new Intent(getActivity(), InteracaoPersonagemActivity.class);
        intent.putExtra("PERSONAGEM", idPersonagem);
        startActivity(intent);
    }
}
