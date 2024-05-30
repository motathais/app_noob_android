package com.example.app_noob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class NovaPartida : AppCompatActivity() {

    lateinit var btnVoltarNovaPartida: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_partida)

        btnVoltarNovaPartida= findViewById(R.id.btnVoltarPerfilUsuario)

        btnVoltarNovaPartida.setOnClickListener(){
            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
        }

        val qtdParticipantes = intent.getStringExtra("QTD_PARTICIPANTES")
    }
}