package io.github.gamification.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import java.util.List;

import io.github.gamification.R;
import io.github.gamification.adapter.AdapterRanking;
import io.github.gamification.model.Usuario;
import io.github.gamification.util.ShowDialog;
import io.github.gamification.viewModel.RankingViewModel;

public class RankingActivity extends AppCompatActivity {

    private AdapterRanking adapterRanking;
    private RecyclerView recyclerViewRanking;
    private RankingViewModel viewModel;

    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);


        adapterRanking = new AdapterRanking(getApplicationContext());
        recyclerViewRanking = findViewById(R.id.recyclerViewRanking);
        recyclerViewRanking.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRanking.setAdapter(adapterRanking);
        recyclerViewRanking.addItemDecoration(new DividerItemDecoration(
                recyclerViewRanking.getContext(), DividerItemDecoration.VERTICAL));
        viewModel = ViewModelProviders.of(this).get(RankingViewModel.class);
        atualizaRanking();

    }


    @Override
    public void onAttachedToWindow() {
        dialog = ShowDialog.showDialogIndeterminado(this, "Carregando ranking...");
        dialog.show();
    }

    private void atualizaRanking() {

        viewModel.getRanking(getApplicationContext()).observe(this, new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                adapterRanking.setUsuarios(usuarios);
                if ((dialog != null) && (dialog.isShowing())){
                    dialog.dismiss();
                }
            }
        });
    }
}