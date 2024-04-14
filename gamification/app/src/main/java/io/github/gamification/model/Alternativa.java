package io.github.gamification.model;

import java.io.Serializable;

public class Alternativa implements Serializable {

    private long id;
    private String texto;
    private boolean correto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isCorreto() {
        return correto;
    }

    public void setCorreto(boolean correto) {
        this.correto = correto;
    }
}
