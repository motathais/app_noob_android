package com.example.app_noob.models

data class LoginResponse(
    val token: String,
    val usuario: UsuarioSearch,
    val msg: String
)