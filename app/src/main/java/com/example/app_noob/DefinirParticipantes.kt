package com.example.app_noob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast

class DefinirParticipantes : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private var valorSelecionado: Int? = null

    private lateinit var btnContinuar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_definir_participantes)

        // Inicializar o RadioGroup
        radioGroup = findViewById(R.id.radioGroup)

        //Inicializando o botão continuar
        btnContinuar = findViewById(R.id.btnIniciar)

        // Configurar listener para capturar a seleção do usuário
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = findViewById(checkedId)
            valorSelecionado = radioButton.tag.toString().toInt()
            Toast.makeText(this, "Selecionado: $valorSelecionado", Toast.LENGTH_SHORT).show()
        }

        btnContinuar.setOnClickListener(){
            val intent = Intent(this@DefinirParticipantes, NovaPartida::class.java).apply {
                putExtra("QTD", valorSelecionado.toString())
            }
            startActivity(intent)
        }
    }

}