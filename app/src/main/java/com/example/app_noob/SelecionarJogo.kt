package com.example.app_noob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.app_noob.models.JogoSearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelecionarJogo : AppCompatActivity() {

    private lateinit var participantes: Array<String?>

    private lateinit var btnIniciarPartida: Button
    private lateinit var spinnerContainerJogo: LinearLayout

    private lateinit var btnVoltarSelecionarJogo: ImageButton

    // Variável para armazenar o jogo selecionado
    private var jogoSelecionado: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selecionar_jogo)

        btnIniciarPartida = findViewById(R.id.btnIniciarPartida)
        spinnerContainerJogo = findViewById(R.id.spinner_container_jogos)
        btnVoltarSelecionarJogo = findViewById(R.id.btnVoltarSelecionarJogo)


        btnVoltarSelecionarJogo.setOnClickListener(){
            val intent = Intent(this@SelecionarJogo, MenuPrincipal::class.java)
            startActivity(intent)
        }

        // Recuperar os dados dos participantes do Intent
        participantes = intent.getStringArrayExtra("PARTICIPANTES") ?: arrayOfNulls(0)

        val baseUrl = "https://api-noob.onrender.com"
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
                    layoutParams.setMargins(0, 16, 0, 16)
                    spinnerJogos.layoutParams = layoutParams

                    // Adicionar o Spinner ao container
                    spinnerContainerJogo.addView(spinnerJogos)
                    Log.d("SelecionarJogo", "Spinner de jogos adicionado")

                    // Listener para armazenar a seleção do usuário
                    spinnerJogos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                            jogoSelecionado = titulos[position]
                            Log.d("SelecionarJogo", "Jogo selecionado: $jogoSelecionado")
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                            // Do nothing
                        }
                    }
                } else {
                    Log.e("SelecionarJogo", "Falha na resposta: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<JogoSearch>>, t: Throwable) {
                Log.e("SelecionarJogo", "Erro na chamada à API", t)
            }
        }
        )

        btnIniciarPartida.setOnClickListener {
            Toast.makeText(this, "Jogo selecionado: $jogoSelecionado", Toast.LENGTH_SHORT).show()

            val intent = Intent(this@SelecionarJogo, Partida::class.java)
            intent.putExtra("JOGO_SELECIONADO", jogoSelecionado)
            intent.putExtra("PARTICIPANTES", participantes)

            startActivity(intent)
        }
    }


}
