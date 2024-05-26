package com.example.app_noob.models

data class UsuarioRequest(
    val nome: String,
    val apelido: String,
    val nascimento: String,
    val email: String,
    val senha: String

)