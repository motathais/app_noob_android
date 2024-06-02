package com.example.app_noob.models

import com.google.gson.annotations.SerializedName

data class PartidaSearch (
    @SerializedName("_id") val id: String?,
    @SerializedName("usuarios") val usuarios: List<UsuarioPartida>,
    @SerializedName("jogo") val jogo: List<JogoPartida>,
    @SerializedName("vencedor") val vencedor: List<UsuarioPartida>,
    @SerializedName("duracao") val duracao: String
)