package io.github.gamification.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.gamification.R;
import io.github.gamification.model.Anotacao;
import io.github.gamification.model.Insignia;

public class AdapterAnotacao extends RecyclerView.Adapter<AdapterAnotacao.AnotacaoViewHolder> {
    private LayoutInflater inflater;
    private List<Anotacao> listaItens;
    private Context ctx;

    public OnBindCallbackAnotacao onBind;

    public AdapterAnotacao(Context ctx) {
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
    }
    @NonNull
    @Override
    public AdapterAnotacao.AnotacaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_anotacao, parent, false);
        return new AdapterAnotacao.AnotacaoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAnotacao.AnotacaoViewHolder holder, int position) {
        if (onBind != null) {
            onBind.onViewBound(holder, position);
        }

        if (listaItens != null) {
            holder.setData(listaItens.get(position));
            //holder.setListners();
        }
    }

    @Override
    public int getItemCount() {
        int tamanhoLista = 0;
        if (listaItens != null)
            tamanhoLista = listaItens.size();

        return tamanhoLista;
    }

    public class AnotacaoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView anotacoesNome;
        AnotacaoViewHolder(@NonNull View itemView) {
            super(itemView);
            anotacoesNome = itemView.findViewById(R.id.anotacoesNome);

        }

        void setData(Anotacao item) {
            anotacoesNome.setText(item.getTitulo());
            String texto = (item.getTexto().length() > 100) ?
                    item.getTexto().substring(0, 100).concat("..."):
                    item.getTexto();
        }

        void setListners() {
            itemView.setOnClickListener(AdapterAnotacao.AnotacaoViewHolder.this);
        }
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION){
                //adicionaProdutoVenda(listaProdutos.get(position));
            }
        }
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public OnBindCallbackAnotacao getOnBind() {
        return onBind;
    }

    public void setOnBind(OnBindCallbackAnotacao onBind) {
        this.onBind = onBind;
    }

    public List<Anotacao> getListaItens() {
        return listaItens;
    }

    public void setListaItens(List<Anotacao> listaItens) {
        this.listaItens = listaItens;
        notifyDataSetChanged();
    }
}
