package io.github.gamification.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.gamification.R;
import io.github.gamification.model.Usuario;

public class AdapterRanking  extends RecyclerView.Adapter<AdapterRanking.RankingViewHolder> {

    private LayoutInflater inflater;
    private List<Usuario> usuarios;

    public OnBindCallbackRanking onBind;


    public AdapterRanking(Context ctx) {
        inflater = LayoutInflater.from(ctx);
    }
    @NonNull
    @Override
    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_ranking, parent, false);
        return new AdapterRanking.RankingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingViewHolder holder, int position) {
        if (onBind != null) {
            onBind.onViewBound(holder, position);
        }

        if (usuarios != null) {
            holder.setData(usuarios.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (usuarios == null){
            return 0;
        }
        return usuarios.size();
    }

    public class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView usuarioPontuacao;
        TextView usuarioNome;

        RankingViewHolder(@NonNull View itemView) {
            super(itemView);
            usuarioPontuacao = itemView.findViewById(R.id.usuarioPontuacao);
            usuarioNome = itemView.findViewById(R.id.usuarioNome);
        }

        void setData(Usuario usuario) {
            usuarioPontuacao.setText(usuario.getPontuacao() + "");
            usuarioNome.setText(usuario.getNome());
        }

        void setListners() {
            itemView.setOnClickListener(AdapterRanking.RankingViewHolder.this);
        }
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION){
                //adicionaProdutoVenda(listaProdutos.get(position));
            }
        }
    }
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
        notifyDataSetChanged();
    }
}
