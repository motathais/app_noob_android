package com.example.app_noob

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.*
import com.example.app_noob.models.UsuarioSearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NovaPartida : AppCompatActivity() {

    lateinit var btnVoltarNovaPartida: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_partida)

            btnVoltarNovaPartida = findViewById(R.id.btnVoltarPerfilUsuario)

            btnVoltarNovaPartida.setOnClickListener {
                val intent = Intent(this, MenuPrincipal::class.java)
                startActivity(intent)
            }

            val qtdParticipantes = intent.getStringExtra("QTD")?.toIntOrNull() ?: 0

            Toast.makeText(this@NovaPartida, "Quantidade de participantes: ${qtdParticipantes}", Toast.LENGTH_SHORT).show()

        // Lista de opções para o Spinner
        //val opcoes = listOf("usuario1", "usuario2", "usuario3")

        // Referência ao LinearLayout onde os Spinners serão adicionados
        val spinnerContainer = findViewById<LinearLayout>(R.id.spinner_container)

        val baseUrl = "https://api-noob.onrender.com" // Substituir pela URL base da API
        val usuarioApi = RetrofitClient.getClient(baseUrl).create(UsuarioApi::class.java)

        val call = usuarioApi.buscarUsuarios()

        call.enqueue(object : Callback<List<UsuarioSearch>> {
            override fun onResponse(call: Call<List<UsuarioSearch>>, response: Response<List<UsuarioSearch>>) {
                if (response.isSuccessful) {
                    val usuarios = response.body() ?: emptyList()
                    val nomes = usuarios.map { it.nome }

                    // Criar e adicionar os Spinners dinamicamente
                    for (i in 1..qtdParticipantes) {
                        val spinner = Spinner(this@NovaPartida)
                        val adapter = ArrayAdapter(this@NovaPartida, android.R.layout.simple_spinner_item, nomes)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinner.adapter = adapter

                        // Configurar LayoutParams para o Spinner com margens
                        val layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        layoutParams.setMargins(0, 16, 0, 16) // Adicionar margens (espaçamento) entre os Spinners
                        spinner.layoutParams = layoutParams

                        // Adicionar o Spinner ao container
                        spinnerContainer.addView(spinner)
                        Log.d("NovaPartida", "Spinner $i adicionado")
                    }
                } else {
                    Log.e("NovaPartida", "Falha na resposta: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<UsuarioSearch>>, t: Throwable) {
                Log.e("NovaPartida", "Erro na chamada à API", t)
            }
        })

        }

    }




