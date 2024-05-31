package io.github.gamification.service;

import io.github.gamification.model.Personagem;
import io.github.gamification.model.RetornoInteracao;
import io.github.gamification.model.Usuario;
import io.github.gamification.model.request.SalvaQuestaoRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PersonagemService {

    @GET("/personagem")
    Call<Personagem> findById(@Query("idPersonagem") int idPersonagem,
                              @Query("idUsuario") long idUsuario);

    @POST("/personagem")
    Call<RetornoInteracao> salvaRespostaQuestaoPersonagem(@Body SalvaQuestaoRequest request);
}
