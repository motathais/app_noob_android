package com.example.app_noob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Spinner
import com.example.app_noob.models.JogoSearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelecionarJogo : AppCompatActivity() {

    lateinit var btnVoltarSelecionarJogo: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selecionar_jogo)

        btnVoltarSelecionarJogo = findViewById(R.id.btnVoltarSelecionarJogo)

        btnVoltarSelecionarJogo.setOnClickListener {
            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
        }

        val baseUrl = "https://api-noob.onrender.com" // Substituir pela URL base da API

        //Spinner de jogos

        val spinnerContainerJogo = findViewById<LinearLayout>(R.id.spinner_container_jogos)

        val jogoApi = RetrofitClient.getClient(baseUrl).create(JogoApi::class.java)

        val callJogos = jogoApi.buscarJogos()

        callJogos.enqueue(object : Callback<List<JogoSearch>> {
            override fun onResponse(call: Call<List<JogoSearch>>, response: Response<List<JogoSearch>>) {
                if (response.isSuccessful) {
                    val jogos = response.body() ?: emptyList()
                    val titulos = jogos.map { it.titulo }

                    val spinnerJogos = Spinner(this@SelecionarJogo)
                    val adapterJogos = ArrayAdapter(
                        this@SelecionarJogo,
                        android.R.layout.simple_spinner_item,
                        titulos
                    )
                    adapterJogos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerJogos.adapter = adapterJogos

                    // Configurar LayoutParams para o Spinner com margens
                    val layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.setMargins(
                        0,
                        16,
                        0,
                        16
                    ) // Adicionar margens (espaçamento) entre os Spinners
                    spinnerJogos.layoutParams = layoutParams

                    // Adicionar o Spinner ao container
                    spinnerContainerJogo.addView(spinnerJogos)
                    Log.d("NovaPartida", "Spinner de jogos adicionado")
                }
            }

            override fun onFailure(call: Call<List<JogoSearch>>, t: Throwable) {
                Log.e("NovaPartida", "Erro na chamada à API", t)
            }
        })
    }
}