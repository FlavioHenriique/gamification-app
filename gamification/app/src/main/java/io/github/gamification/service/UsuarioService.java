package io.github.gamification.service;

import java.util.List;

import io.github.gamification.model.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UsuarioService {

    @GET("/usuario/login")
    Call<Usuario> login(@Query("email") String email, @Query("senha") String senha);

    @POST("/usuario")
    Call<Usuario> cadastro(@Body Usuario usuario);

    @GET("/usuario/ranking")
    Call<List<Usuario>> ranking();

    @GET("/usuario")
    Call<Usuario> find(@Query("id") long id);
}
