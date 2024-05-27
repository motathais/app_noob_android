package com.example.app_noob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MenuPrincipal : AppCompatActivity() {

    private lateinit var btnVoltarMenuPrincipal: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

       btnVoltarMenuPrincipal = findViewById(R.id.btnVoltarMenuPrincipal)

        btnVoltarMenuPrincipal.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }


}