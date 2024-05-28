package com.example.app_noob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MenuPrincipal : AppCompatActivity() {

    private lateinit var btnVoltarMenuPrincipal: ImageButton
    private lateinit var btnRegistrarPartida: ImageButton
    private lateinit var btnVisualizarPartida: ImageButton
    private lateinit var btnCadastrarJogo: ImageButton
    private lateinit var  btnAlterarPerfil: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        btnVoltarMenuPrincipal = findViewById(R.id.btnVoltarNovaPartida)
        btnRegistrarPartida = findViewById(R.id.btnRegistrarPartida)
        btnVisualizarPartida = findViewById(R.id.btnVisualizarPartida)
        btnCadastrarJogo = findViewById(R.id.btnCadastrarJogo)
        btnAlterarPerfil = findViewById(R.id.btnAlterarPerfil)


        btnVoltarMenuPrincipal.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnRegistrarPartida.setOnClickListener(){
            val intent = Intent(this, NovaPartida::class.java)
            startActivity(intent)
        }

        btnVisualizarPartida.setOnClickListener(){
            val intent = Intent(this, ListaPartidas::class.java)
            startActivity(intent)
        }

        btnCadastrarJogo.setOnClickListener(){
            val intent = Intent(this, CadastroJogos::class.java)
            startActivity(intent)
        }

        btnAlterarPerfil.setOnClickListener(){
            val intent = Intent(this, PerfilUsuario::class.java)
            startActivity(intent)
        }
    }
}