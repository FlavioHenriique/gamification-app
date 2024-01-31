package io.github.gamification.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import io.github.gamification.R;
import io.github.gamification.adapter.AdapterInsigniaItem;
import io.github.gamification.model.Insignia;
import io.github.gamification.viewModel.InsigniaViewModel;

public class InsigniaActivity extends AppCompatActivity {

    private InsigniaViewModel viewModel;
    private AdapterInsigniaItem adapterInsigniaItem;
    private RecyclerView recyclerViewInsignia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insignia);

        adapterInsigniaItem = new AdapterInsigniaItem(this);
        recyclerViewInsignia = findViewById(R.id.recyclerViewInsignias);
        recyclerViewInsignia.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewInsignia.setAdapter(adapterInsigniaItem);
        recyclerViewInsignia.addItemDecoration(new DividerItemDecoration(
                recyclerViewInsignia.getContext(), DividerItemDecoration.VERTICAL));
        viewModel = ViewModelProviders.of(this).get(InsigniaViewModel.class);
        atualizaInsignias();
    }

    private void atualizaInsignias() {
        viewModel.getInsignias(this).observe(this, new Observer<List<Insignia>>() {
            @Override
            public void onChanged(List<Insignia> insignias) {
                adapterInsigniaItem.setListaItens(insignias);
            }
        });
    }
}