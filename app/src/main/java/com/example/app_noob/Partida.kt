package com.example.app_noob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageButton
import android.widget.Toast
import com.example.app_noob.models.JogoPartida
import com.example.app_noob.models.PartidaRequest
import com.example.app_noob.models.PartidaResponse
import com.example.app_noob.models.UsuarioPartida
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Partida : AppCompatActivity() {

    private lateinit var btnVoltarPartida: ImageButton
    private lateinit var participantes: Array<String?>
    private lateinit var cronometro: Chronometer
    private lateinit var btnSalvarPartida: Button
    private var milissegundosCorridos: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partida)

        btnVoltarPartida = findViewById(R.id.btnVoltarPartida)
        cronometro = findViewById(R.id.chronometer)
        btnSalvarPartida = findViewById(R.id.btnSalvarPartida)

        btnVoltarPartida.setOnClickListener() {
            val intent = Intent(this@Partida, MenuPrincipal::class.java)
            startActivity(intent)
        }

        // Recuperar os dados dos participantes do Intent
        participantes = intent.getStringArrayExtra("PARTICIPANTES") ?: arrayOfNulls(0)
        val jogo = intent.getStringExtra("JOGO_SELECIONADO")

        // Start the chronometer
        cronometro.base = SystemClock.elapsedRealtime()
        cronometro.start()

        btnSalvarPartida.setOnClickListener {
            // Stop the chronometer
            cronometro.stop()

            // Calculate elapsed time
            milissegundosCorridos = SystemClock.elapsedRealtime() - cronometro.base
            val duracao = "${milissegundosCorridos / 1000} segundos"

            // Criação dos objetos necessários para a API
            val usuarioList = participantes.map { UsuarioPartida(it ?: "") }
            val jogoList = listOf(JogoPartida(jogo ?: ""))
            val vencedor = usuarioList.first() // Ou determine o vencedor de outra forma

            val atividade = PartidaRequest(
                usuarios = usuarioList,
                jogo = jogoList,
                vencedor = vencedor,
                duracao = duracao
            )

            // Enviar dados para a API usando Retrofit
            val baseUrl = "https://api-noob.onrender.com" // Substituir pela URL base da API
            val atividadeApi = RetrofitClient.getClient(baseUrl).create(AtividadeApi::class.java)
            val call = atividadeApi.criarAtividade(atividade)

            call.enqueue(object : Callback<PartidaResponse> {
                override fun onResponse(call: Call<PartidaResponse>, response: Response<PartidaResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@Partida, "Partida registrada com sucesso!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@Partida, "Erro ao registrar a partida: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<PartidaResponse>, t: Throwable) {
                    Toast.makeText(this@Partida, "Erro na requisição: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })

        }
    }

}