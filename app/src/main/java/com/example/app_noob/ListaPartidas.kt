package com.example.app_noob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class ListaPartidas : AppCompatActivity() {

    lateinit var btnVoltarListaPartidas: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_partidas)

        btnVoltarListaPartidas = findViewById(R.id.btnVoltarSelecionarJogo)

        btnVoltarListaPartidas.setOnClickListener(){
            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
        }
    }
}