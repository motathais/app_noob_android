package com.example.app_noob

import com.example.app_noob.models.*
import retrofit2.Call
import retrofit2.http.*

interface UsuarioApi {
    @POST("api/usuarios")
    fun cadastrarUsuario(@Body usuarioRequest: UsuarioRequest): Call<UsuarioResponse>

    @GET("api/usuarios")
    fun buscarUsuarios(): Call<List<UsuarioSearch>>

    @GET("api/usuarios/{id}")
    fun buscarUsuario(@Path("id") userId: String): Call<UsuarioSearch>

    @PUT("api/usuarios/{id}")
    fun atualizarUsuario(@Path("id") userId: String, @Body usuarioSearch: UsuarioSearch): Call<Void>
}

interface LoginApi {
    @POST("/api/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}

interface JogoApi {
    @POST("/api/jogos")
    fun cadastrarJogo(@Body jogoRequest: JogoRequest): Call<JogoResponse>

    @GET("api/jogos")
    fun buscarJogos(): Call<List<JogoSearch>>
}

interface AtividadeApi {
    @POST("/api/atividades")
    fun criarAtividade(@Body atividade: PartidaRequest): Call <PartidaResponse>

    @GET("api/atividades")
    fun buscarAtividades(): Call<List<PartidaSearch>>

    @DELETE("api/atividades/{id}")
    fun deletarAtividade(@Path("id") id: String): Call<Void>
}



