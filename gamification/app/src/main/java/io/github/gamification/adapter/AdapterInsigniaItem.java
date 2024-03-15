package io.github.gamification.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.gamification.R;
import io.github.gamification.model.Insignia;

public class AdapterInsigniaItem extends RecyclerView.Adapter<AdapterInsigniaItem.InsigniaItemViewHolder> {
    private LayoutInflater inflater;
    private List<Insignia> listaItens;
    private Context ctx;

    public OnBindCallbackInsigniaItem onBind;

    public AdapterInsigniaItem(Context ctx) {
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
    }
    @NonNull
    @Override
    public AdapterInsigniaItem.InsigniaItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_insignia, parent, false);
        return new AdapterInsigniaItem.InsigniaItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterInsigniaItem.InsigniaItemViewHolder holder, int position) {
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

    public class InsigniaItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView insigniaItemTitulo;
        TextView insigniaItemDescricao;
        TextView insigniaItemPercentual;

        ConstraintLayout insigniaItemLayout;

        InsigniaItemViewHolder(@NonNull View itemView) {
            super(itemView);
            insigniaItemTitulo = itemView.findViewById(R.id.insigniaItemTitulo);
            insigniaItemDescricao = itemView.findViewById(R.id.insigniaItemDescricao);
            insigniaItemPercentual = itemView.findViewById(R.id.insigniaItemPercentual);
            insigniaItemLayout = itemView.findViewById(R.id.insigniaItemLayout);
        }

        void setData(Insignia item) {
            insigniaItemTitulo.setText(item.getNome());
            insigniaItemDescricao.setText(item.getDescricao());
            insigniaItemPercentual.setText(item.getPercentualUsuarios() + "%");
            if (item.isConquistada())
                insigniaItemLayout.setBackgroundColor(Color.parseColor("#DCDCDC"));
        }

        void setListners() {
            itemView.setOnClickListener(AdapterInsigniaItem.InsigniaItemViewHolder.this);
        }
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION){
                //adicionaProdutoVenda(listaProdutos.get(position));
            }
        }
    }

    public OnBindCallbackInsigniaItem getOnBind() {
        return onBind;
    }

    public void setOnBind(OnBindCallbackInsigniaItem onBind) {
        this.onBind = onBind;
    }


    public List<Insignia> getListaItens() {
        return listaItens;
    }

    public void setListaItens(List<Insignia> listaItens) {
        this.listaItens = listaItens;
        notifyDataSetChanged();
    }
}
