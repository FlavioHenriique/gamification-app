package io.github.gamification.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RetornoInteracao implements Serializable {
    private List<Insignia> insigniasLiberadas = new ArrayList<>();
    private List<Anotacao> anotacoesLiberadas = new ArrayList<>();

    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Insignia> getInsigniasLiberadas() {
        return insigniasLiberadas;
    }

    public void setInsigniasLiberadas(List<Insignia> insigniasLiberadas) {
        this.insigniasLiberadas = insigniasLiberadas;
    }

    public List<Anotacao> getAnotacoesLiberadas() {
        return anotacoesLiberadas;
    }

    public void setAnotacoesLiberadas(List<Anotacao> anotacoesLiberadas) {
        this.anotacoesLiberadas = anotacoesLiberadas;
    }
}
