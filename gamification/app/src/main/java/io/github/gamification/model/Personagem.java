package io.github.gamification.model;

import java.io.Serializable;
import java.util.List;

public class Personagem implements Serializable {
    private int id;
    private String nome;
    private List<Dialogo> linhasDialogo;
    private int ultimaResposta;

    public int getUltimaResposta() {
        return ultimaResposta;
    }

    public void setUltimaResposta(int ultimaResposta) {
        this.ultimaResposta = ultimaResposta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Dialogo> getLinhasDialogo() {
        return linhasDialogo;
    }

    public void setLinhasDialogo(List<Dialogo> linhasDialogo) {
        this.linhasDialogo = linhasDialogo;
    }
}
