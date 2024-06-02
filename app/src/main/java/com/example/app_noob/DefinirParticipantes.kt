package com.example.app_noob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class DefinirParticipantes : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private var valorSelecionado: Int? = null
    private lateinit var btnVoltarDefinirParticipantes: ImageButton
    private lateinit var btnContinuar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_definir_participantes)

        // Obter o nome do usuário passado pela MainActivity
        val userName = intent.getStringExtra("USER_NAME")
        val userId = intent.getStringExtra("USER_ID")

        btnVoltarDefinirParticipantes = findViewById(R.id.btnVoltarPartida)

        // Inicializar o RadioGroup
        radioGroup = findViewById(R.id.radioGroup)

        //Inicializando o botão continuar
        btnContinuar = findViewById(R.id.btnIniciarPartida)

        // Configurar listener para capturar a seleção do usuário
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = findViewById(checkedId)
            valorSelecionado = radioButton.tag.toString().toInt()
            Toast.makeText(this, "Selecionado: $valorSelecionado", Toast.LENGTH_SHORT).show()
        }

        btnContinuar.setOnClickListener(){
            val intent = Intent(this@DefinirParticipantes, NovaPartida::class.java).apply {
                putExtra("QTD", valorSelecionado.toString())
                putExtra("USER_NAME",userName)
                putExtra("USER_ID",userId)
            }
            startActivity(intent)
        }

        btnVoltarDefinirParticipantes.setOnClickListener(){
            val intent = Intent(this@DefinirParticipantes, MenuPrincipal::class.java).apply {
                putExtra("USER_NAME",userName)
                putExtra("USER_ID",userId)
            }
            startActivity(intent)
        }

    }

}