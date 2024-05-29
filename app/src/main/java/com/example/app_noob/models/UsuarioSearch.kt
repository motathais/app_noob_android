package com.example.app_noob.models
import org.bson.types.ObjectId


data class UsuarioSearch(
        val _id: String,
        val nome: String,
        val apelido: String,
        val nascimento: String,
        val email: String,
        val senha: String
)
