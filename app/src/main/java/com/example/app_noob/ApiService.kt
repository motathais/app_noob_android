package com.example.app_noob

import com.example.app_noob.models.LoginRequest
import com.example.app_noob.models.LoginResponse
import com.example.app_noob.models.UsuarioRequest
import com.example.app_noob.models.UsuarioResponse
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

