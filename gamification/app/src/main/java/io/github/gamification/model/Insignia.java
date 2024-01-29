package io.github.gamification.model;


public class Insignia {

    private long id;
    private String nome;
    private String descricao;
    private String imagem;
    private boolean conquistada;

    public Insignia(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public boolean isConquistada() {
        return conquistada;
    }

    public void setConquistada(boolean conquistada) {
        this.conquistada = conquistada;
    }
}
