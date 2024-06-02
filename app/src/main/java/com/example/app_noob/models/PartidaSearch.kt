package com.example.app_noob.models

import com.google.gson.annotations.SerializedName
import org.bson.types.ObjectId


/*data class PartidaSearch (
    val id: String,
    val usuarios: List<UsuarioPartida>,
    val jogo: List<JogoPartida>,
    val vencedor: List<UsuarioPartida>,
    val duracao: String
)*/

data class PartidaSearch (
    @SerializedName("_id") val id: String?,
    @SerializedName("usuarios") val usuarios: List<UsuarioPartida>,
    @SerializedName("jogo") val jogo: List<JogoPartida>,
    @SerializedName("vencedor") val vencedor: List<UsuarioPartida>,
    @SerializedName("duracao") val duracao: String
)