package com.example.app_noob.models

data class PartidaRequest (
    val usuarios: List<UsuarioPartida>,
    val jogo: List<JogoPartida>,
    val vencedor: List<UsuarioPartida>,
    val duracao: String
)