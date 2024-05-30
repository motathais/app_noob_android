package com.example.app_noob

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.*

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
        val opcoes = listOf("usuario1", "usuario2", "usuario3")

        // Referência ao LinearLayout onde os Spinners serão adicionados
        val spinnerContainer = findViewById<LinearLayout>(R.id.spinner_container)

        // Criar e adicionar os Spinners dinamicamente
        for (i in 1..qtdParticipantes) {
            val spinner = Spinner(this)
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcoes)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            // Configurar LayoutParams para o Spinner
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            spinner.layoutParams = layoutParams

            //spinner.setBackgroundColor(Color.RED) // Apenas para testes visuais

            // Adicionar o Spinner ao container
            spinnerContainer.addView(spinner)
        }

    }
}



