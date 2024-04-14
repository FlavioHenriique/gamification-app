package io.github.gamification.model;

import java.io.Serializable;
import java.util.List;

public class Dialogo implements Serializable {
    private String mensagem;
    private List<Alternativa> opcoesResposta;
    private int imagem;
    private boolean ultima;
    private int idQuestao;

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public List<Alternativa> getOpcoesResposta() {
        return opcoesResposta;
    }

    public void setOpcoesResposta(List<Alternativa> opcoesResposta) {
        this.opcoesResposta = opcoesResposta;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    public boolean isUltima() {
        return ultima;
    }

    public void setUltima(boolean ultima) {
        this.ultima = ultima;
    }

    public int getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(int idQuestao) {
        this.idQuestao = idQuestao;
    }
}
