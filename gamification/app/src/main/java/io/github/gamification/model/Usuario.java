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

    private List<Insignia> insignias;

    private List<Resposta> respostas;

    private List<Anotacao> anotacoes;

    private long posicaoRanking;

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

    public List<Insignia> getInsignias() {
        return insignias;
    }

    public void setInsignias(List<Insignia> insignias) {
        this.insignias = insignias;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }

    public List<Anotacao> getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(List<Anotacao> anotacoes) {
        this.anotacoes = anotacoes;
    }

    public long getPosicaoRanking() {
        return posicaoRanking;
    }

    public void setPosicaoRanking(long posicaoRanking) {
        this.posicaoRanking = posicaoRanking;
    }
}
