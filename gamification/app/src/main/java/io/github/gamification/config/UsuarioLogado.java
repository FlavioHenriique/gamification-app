package io.github.gamification.config;

import android.app.Application;

import io.github.gamification.model.Usuario;
import io.github.gamification.service.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioLogado extends Application {

    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void atualizaUsuario(){
        Call<Usuario> find = RetrofitInstance.getRetrofitInstance()
                .create(UsuarioService.class).find(usuario.getId());

        find.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.body() != null){
                    usuario = response.body();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}
