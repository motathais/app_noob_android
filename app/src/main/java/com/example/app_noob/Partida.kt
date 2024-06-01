package com.example.app_noob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageButton
import android.widget.Toast

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

        Toast.makeText(
            this@Partida,
            "Jogo Selecionado: ${jogo} e o primeiro participante é: ${participantes[0]}",
            Toast.LENGTH_SHORT
        ).show()

        // Start the chronometer
        cronometro.base = SystemClock.elapsedRealtime()
        cronometro.start()

        btnSalvarPartida.setOnClickListener {
            // Stop the chronometer
            cronometro.stop()

            // Calculate elapsed time
            milissegundosCorridos = SystemClock.elapsedRealtime() - cronometro.base

            // Show the elapsed time (optional)
            Toast.makeText(
                this,
                "Tempo: ${milissegundosCorridos / 1000} segundos",
                Toast.LENGTH_SHORT
            ).show()

            // Here you can use the elapsed time as needed
            // For example, you can save it to a variable or pass it to another activity

        }
    }

}