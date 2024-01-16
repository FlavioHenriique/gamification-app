package io.github.gamification.config;

import android.app.Application;

import io.github.gamification.model.Usuario;

public class UsuarioLogado extends Application {

    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
