package io.github.gamification.viewModel;
import android.content.Context;
import androidx.annotation.NonNull;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;

import java.util.List;

import io.github.gamification.config.UsuarioLogado;
import io.github.gamification.model.Insignia;

public class InsigniaViewModel extends AndroidViewModel {
    private String TAG = this.getClass().getSimpleName();
    MutableLiveData<List<Insignia>> listaInsignia;
    public InsigniaViewModel(@NonNull Application application) {
        super(application);
        listaInsignia = new MutableLiveData<>();
    }

    public LiveData<List<Insignia>> getInsignias(Context context) {
        UsuarioLogado usuarioLogado = (UsuarioLogado) context.getApplicationContext();
        listaInsignia.setValue(usuarioLogado.getUsuario().getInsigniasConquistadas());
        return listaInsignia;
    }
}
