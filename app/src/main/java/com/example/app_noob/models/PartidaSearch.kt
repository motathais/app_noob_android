package com.example.app_noob.models

data class PartidaSearch (
    val id: String,
    val usuario: List<UsuarioSearch>,
    val jogo: List<JogoSearch>,
    val vencedor: UsuarioSearch,
    val duracao: String
)