package com.example.app_noob

import com.example.app_noob.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioApi {
    @POST("api/usuarios")
    fun cadastrarUsuario(@Body usuarioRequest: UsuarioRequest): Call<UsuarioResponse>
}

interface LoginApi {
    @POST("/api/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}

interface JogoApi {
    @POST("/api/jogos")
    fun cadastrarJogo(@Body jogoRequest: JogoRequest): Call<JogoResponse>
}

