package io.github.gamification.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import io.github.gamification.R;
import io.github.gamification.adapter.AdapterInsigniaItem;
import io.github.gamification.model.Insignia;
import io.github.gamification.viewModel.InsigniaViewModel;

public class InsigniaActivity extends AppCompatActivity {

    private InsigniaViewModel viewModel;
    private AdapterInsigniaItem adapterInsigniaItem;
    private RecyclerView recyclerViewInsignia;

    private Toolbar my_toolbar;


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

        my_toolbar = findViewById(R.id.my_toolbar);

        setSupportActionBar(my_toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        my_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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