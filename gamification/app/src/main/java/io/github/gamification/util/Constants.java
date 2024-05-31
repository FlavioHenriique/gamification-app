package io.github.gamification.util;

import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final int ID_PERSONAGEM_FABIANO = 1;
    public static final int ID_PERSONAGEM_LUCAS = 2;
    public static final int ID_PERSONAGEM_MARCELA = 3;
    public static final int ID_PERSONAGEM_JACQUELINE = 4;
    public static final int ID_PERSONAGEM_IAN = 5;
    public static final int ID_PERSONAGEM_TULIO = 6;

    public static List<Integer> getListaPersonagens(){
        return Arrays.asList(
                ID_PERSONAGEM_FABIANO,
                ID_PERSONAGEM_LUCAS,
                ID_PERSONAGEM_MARCELA,
                ID_PERSONAGEM_JACQUELINE,
                ID_PERSONAGEM_IAN,
                ID_PERSONAGEM_TULIO
        );
    }
}
