package io.github.gamification.model;

import java.io.Serializable;
import java.util.List;

public class Usuario implements Serializable {
    private String nome;

    private long id;
    private String email;
    private String senha;
    private String usuario;
    private long pontuacao;

    private boolean visualizouPrimeiraPagina;

    private List<Insignia> insigniasConquistadas;

    private List<Questao> questoesRespondidas;

    private List<Anotacao> anotacoes;

    public Usuario(){

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public long getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(long pontuacao) {
        this.pontuacao = pontuacao;
    }

    public boolean isVisualizouPrimeiraPagina() {
        return visualizouPrimeiraPagina;
    }

    public void setVisualizouPrimeiraPagina(boolean visualizouPrimeiraPagina) {
        this.visualizouPrimeiraPagina = visualizouPrimeiraPagina;
    }

    public List<Insignia> getInsigniasConquistadas() {
        return insigniasConquistadas;
    }

    public void setInsigniasConquistadas(List<Insignia> insigniasConquistadas) {
        this.insigniasConquistadas = insigniasConquistadas;
    }

    public List<Questao> getQuestoesRespondidas() {
        return questoesRespondidas;
    }

    public void setQuestoesRespondidas(List<Questao> questoesRespondidas) {
        this.questoesRespondidas = questoesRespondidas;
    }

    public List<Anotacao> getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(List<Anotacao> anotacoes) {
        this.anotacoes = anotacoes;
    }
}
