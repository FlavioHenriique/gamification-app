package io.github.gamification.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.github.gamification.activity.AnotacaoActivity;
import io.github.gamification.adapter.AdapterAnotacao;
import io.github.gamification.config.UsuarioLogado;
import io.github.gamification.databinding.FragmentSlideshowBinding;
import io.github.gamification.model.Usuario;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private RecyclerView recyclerViewAnotacoes;

    private AdapterAnotacao adapterAnotacao;

    private Usuario usuario;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerViewAnotacoes = binding.recyclerViewAnotacoes;
        adapterAnotacao = new AdapterAnotacao(getActivity());
        recyclerViewAnotacoes.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAnotacoes.setAdapter(adapterAnotacao);
        recyclerViewAnotacoes.addItemDecoration(new DividerItemDecoration(
                recyclerViewAnotacoes.getContext(), DividerItemDecoration.VERTICAL));

        UsuarioLogado usuarioLogado = (UsuarioLogado) getActivity().getApplicationContext();
        usuario = usuarioLogado.getUsuario();

        adapterAnotacao.setListaItens(usuarioLogado.getUsuario().getAnotacoes());
        if (usuarioLogado.getUsuario().getAnotacoes().isEmpty()){
            final TextView textView = binding.textSlideshow;
            slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        }
        adapterAnotacao.onBind = (viewHolder, position) ->{
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AnotacaoActivity.class);
                    intent.putExtra("TITULO", adapterAnotacao.getListaItens().get(position).getTitulo());
                    intent.putExtra("TEXTO", adapterAnotacao.getListaItens().get(position).getTexto());
                    startActivity(intent);
                }
            });
        };



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}