package com.example.app_noob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.text.InputType
import android.widget.*
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
    private lateinit var pontosEditTexts: Array<EditText?>
    private lateinit var btnSalvarPartida: Button
    private var milissegundosCorridos: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partida)

        btnVoltarPartida = findViewById(R.id.btnVoltarPartida)
        cronometro = findViewById(R.id.chronometer)
        btnSalvarPartida = findViewById(R.id.btnSalvarPartida)

        // Obter o nome do usuário passado pela MainActivity
        val userName = intent.getStringExtra("USER_NAME")
        val userId = intent.getStringExtra("USER_ID")

        btnVoltarPartida.setOnClickListener {
            val intent = Intent(this@Partida, SelecionarJogo::class.java).apply {
                putExtra("USER_NAME",userName)
                putExtra("USER_ID",userId)
            }
            startActivity(intent)
        }

        // Recuperar os dados dos participantes do Intent
        participantes = intent.getStringArrayExtra("PARTICIPANTES") ?: arrayOfNulls(0)
        val jogo = intent.getStringExtra("JOGO_SELECIONADO")


        // Inicializar o array de EditTexts
        pontosEditTexts = arrayOfNulls(participantes.size)

        // Obter o LinearLayout onde os EditTexts serão adicionados
        val pontosContainer = findViewById<LinearLayout>(R.id.pontosContainer)


        // Adicionar EditTexts dinamicamente
        for (i in participantes.indices) {
            val editText = EditText(this)
            editText.hint = "Pontos de ${participantes[i]}"
            editText.inputType = InputType.TYPE_CLASS_NUMBER
            pontosContainer.addView(editText)
            pontosEditTexts[i] = editText
        }

        // Start the chronometer
        cronometro.base = SystemClock.elapsedRealtime()
        cronometro.start()

        btnSalvarPartida.setOnClickListener {
            // Stop the chronometer
            cronometro.stop()

            // Calculate elapsed time
            milissegundosCorridos = SystemClock.elapsedRealtime() - cronometro.base
            val duracao = "${milissegundosCorridos / 1000} segundos"

            // Coletar os pontos dos EditTexts
            val pontos = pontosEditTexts.map { it?.text.toString().toIntOrNull() ?: 0 }
            val maxPontos = pontos.maxOrNull() ?: 0
            val vencedoresIndices = pontos.withIndex().filter { it.value == maxPontos }.map { it.index }

            // Criação dos objetos necessários para a API
            val usuarioList = participantes.map { UsuarioPartida(it ?: "") }
            val jogoList = listOf(JogoPartida(jogo ?: ""))
            val vencedores = vencedoresIndices.map { usuarioList[it] }

            val atividade = PartidaRequest(
                usuarios = usuarioList,
                jogo = jogoList,
                vencedor = vencedores,
                duracao = duracao
            )

            // Enviar dados para a API usando Retrofit
            val baseUrl = "https://api-noob.onrender.com" // Substituir pela URL base da API
            val atividadeApi = RetrofitClient.getClient(baseUrl).create(AtividadeApi::class.java)
            val call = atividadeApi.criarAtividade(atividade)

            call.enqueue(object : Callback<PartidaResponse> {
                override fun onResponse(call: Call<PartidaResponse>, response: Response<PartidaResponse>) {
                    if (response.isSuccessful) {
                        val vencedorNomes = vencedores.joinToString(", ") { it.nome }
                        Toast.makeText(this@Partida, "${response.body()!!.msg}. Vencedor: $vencedorNomes", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@Partida, MenuPrincipal::class.java).apply {
                            putExtra("USER_NAME",userName)
                            putExtra("USER_ID",userId)
                        }
                        startActivity(intent)

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