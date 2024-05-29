package com.example.app_noob

import com.example.app_noob.models.*
import org.bson.types.ObjectId
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Path

interface UsuarioApi {
    @POST("api/usuarios")
    fun cadastrarUsuario(@Body usuarioRequest: UsuarioRequest): Call<UsuarioResponse>

    @GET("api/usuarios/{id}")
    fun buscarUsuario(@Path("id") userId: String): Call<UsuarioSearch>
}

interface LoginApi {
    @POST("/api/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}

interface JogoApi {
    @POST("/api/jogos")
    fun cadastrarJogo(@Body jogoRequest: JogoRequest): Call<JogoResponse>
}





