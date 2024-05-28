package com.example.app_noob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class PerfilUsuario : AppCompatActivity() {

    lateinit var btnVoltarPerfilUsuario: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_usuario)

        btnVoltarPerfilUsuario= findViewById(R.id.btnVoltarPerfilUsuario)

        btnVoltarPerfilUsuario.setOnClickListener(){
            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
        }
    }
}