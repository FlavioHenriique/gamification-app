package io.github.gamification.viewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.github.gamification.config.RetrofitInstance;
import io.github.gamification.config.UsuarioLogado;
import io.github.gamification.model.Usuario;
import io.github.gamification.service.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankingViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    MutableLiveData<List<Usuario>> ranking;
    public RankingViewModel(@NonNull Application application) {
        super(application);
        ranking = new MutableLiveData<>();
    }

    public LiveData<List<Usuario>> getRanking(Context context) {
        Call<List<Usuario>> call = RetrofitInstance.getRetrofitInstance()
                .create(UsuarioService.class).ranking();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.body() != null){
                    ranking.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                System.out.println("erro na consulta de ranking: " + t.getMessage());
            }
        });
        return ranking;
    }
}
