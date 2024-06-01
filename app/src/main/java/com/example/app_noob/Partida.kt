package com.example.app_noob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast

class Partida : AppCompatActivity() {

    private lateinit var btnVoltarPartida: ImageButton
    private lateinit var participantes: Array<String?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partida)

        btnVoltarPartida = findViewById(R.id.btnVoltarPartida)

        btnVoltarPartida.setOnClickListener(){
            val intent = Intent(this@Partida, MenuPrincipal::class.java)
            startActivity(intent)
        }

        // Recuperar os dados dos participantes do Intent
        participantes = intent.getStringArrayExtra("PARTICIPANTES") ?: arrayOfNulls(0)

        val jogo = intent.getStringExtra("JOGO_SELECIONADO")

        Toast.makeText(this@Partida, "Jogo Selecionado: ${jogo} e o primeiro participante é: ${participantes[0]}", Toast.LENGTH_SHORT).show()
    }
}