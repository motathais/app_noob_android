package com.example.app_noob

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app_noob.models.LoginRequest
import com.example.app_noob.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var txtApelido: EditText
    private lateinit var txtSenha: EditText
    private lateinit var btnLogin: Button
    private lateinit var txtCadastrarUsuario: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtApelido = findViewById(R.id.txtApelido)
        txtSenha = findViewById(R.id.txtSenha)
        btnLogin = findViewById(R.id.btnLogin)
        txtCadastrarUsuario = findViewById(R.id.txtCadastrarUsuario)

        txtCadastrarUsuario.setOnClickListener {
            val intent = Intent(this, CadastroUsuario::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val apelido = txtApelido.text.toString()
            val senha = txtSenha.text.toString()

            if (apelido.isNotEmpty() && senha.isNotEmpty()) {
                login(apelido, senha)
            } else {
                Toast.makeText(this, "Por favor, insira o apelido e a senha!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login(apelido: String, senha: String) {
        val apiService = RetrofitClient.getClient("https://api-noob.onrender.com").create(LoginApi::class.java)
        val call = apiService.login(LoginRequest(apelido, senha))

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!
                    Toast.makeText(this@MainActivity, response.body()!!.msg, Toast.LENGTH_SHORT).show()

                    // Iniciar a nova atividade após o login bem-sucedido
                    val intent = Intent(this@MainActivity, MenuPrincipal::class.java).apply {
                        putExtra("USER_NAME", loginResponse.usuario.apelido)
                        putExtra("USER_ID", loginResponse.usuario.id)
                    }
                    startActivity(intent)

                } else {
                    Toast.makeText(this@MainActivity, "Usuário ou senha incorretos, insira novamente!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Ocorreu um erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
}
}