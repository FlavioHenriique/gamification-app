package io.github.gamification.model;

import java.io.Serializable;
import java.util.List;

public class Questao {
    private long id;
    private String enunciado;
    private List<Alternativa> alternativas;

    private boolean acertou;


    public static  class Alternativa implements Serializable {
        private long id;
        private String texto;
        private boolean correta;
    }
}
