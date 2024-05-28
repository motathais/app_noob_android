package com.example.app_noob

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class CadastroJogos : AppCompatActivity() {

    lateinit var btnVoltarCadastroJogos: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_jogos)

        btnVoltarCadastroJogos = findViewById(R.id.btnVoltarCadastroJogos)

        btnVoltarCadastroJogos.setOnClickListener(){
            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
        }
    }
}