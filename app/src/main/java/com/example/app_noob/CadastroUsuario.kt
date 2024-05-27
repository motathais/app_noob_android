package com.example.app_noob

import android.content.Intent
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.app_noob.models.UsuarioRequest;
import com.example.app_noob.models.UsuarioResponse;
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import retrofit2.Callback;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Response;


class CadastroUsuario : AppCompatActivity() {

    private lateinit var txtNomeUsuario: EditText
    private lateinit var txtApelidoUsuario: EditText
    private lateinit var txtEmailUsuario: EditText
    private lateinit var txtNascimentoUsuario: EditText
    private lateinit var txtSenhaUsuario: EditText
    private lateinit var txtConfirmarSenhaUsuario: EditText
    private lateinit var btnCadastrarUsuario: Button
    private lateinit var btnVoltarCadastroUsuario: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_usuario)


        // Inicializando as views usando findViewById
        txtNomeUsuario = findViewById(R.id.txtNomeUsuario)
        txtApelidoUsuario = findViewById(R.id.txtApelidoUsuario)
        txtNascimentoUsuario = findViewById(R.id.txtNascimentoUsuario)
        txtEmailUsuario = findViewById(R.id.txtEmailUsuario)
        txtSenhaUsuario = findViewById(R.id.txtSenhaUsuario)
        txtSenhaUsuario = findViewById(R.id.txtConfirmarSenhaUsuario)
        btnCadastrarUsuario = findViewById(R.id.btnCadastrarUsuario)
        btnVoltarCadastroUsuario = findViewById(R.id.btnVoltarMenuPrincipal)

        btnCadastrarUsuario.setOnClickListener {
            if(validarCampos()) {
                val nome = txtNomeUsuario.text.toString()
                val apelido = txtApelidoUsuario.text.toString()
                val nascimento = txtNascimentoUsuario.text.toString()
                val email = txtEmailUsuario.text.toString()
                val senha = txtSenhaUsuario.text.toString()

                val usuario = UsuarioRequest(nome, apelido, nascimento, email, senha)
                cadastrarUsuario(usuario)
            }
        }

        btnVoltarCadastroUsuario.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


    private fun cadastrarUsuario(usuario: UsuarioRequest) {
        val baseUrl = "https://web-eg08riks0c18.up-de-fra1-k8s-1.apps.run-on-seenode.com" // Substitua pela URL base da sua API
        val usuarioApi = RetrofitClient.getClient(baseUrl).create(UsuarioApi::class.java)
        val call = usuarioApi.cadastrarUsuario(usuario)

        call.enqueue(object : Callback<UsuarioResponse> {
            override fun onResponse(call: Call<UsuarioResponse>, response: Response<UsuarioResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    //Toast.makeText(this@CadastroUsuario, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this@CadastroUsuario, response.body()!!.message, Toast.LENGTH_SHORT).show()
                    limparCampos()
                } else {
                    Toast.makeText(this@CadastroUsuario, "Erro no cadastro: ${response.body()!!.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UsuarioResponse>, t: Throwable) {
                Toast.makeText(this@CadastroUsuario, "Erro na comunicação: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun limparCampos(){
        txtNomeUsuario.text.clear()
        txtApelidoUsuario.text.clear()
        txtNascimentoUsuario.text.clear()
        txtEmailUsuario.text.clear()
        txtSenhaUsuario.text.clear()
        txtConfirmarSenhaUsuario.text.clear()
    }

    private fun validarCampos(): Boolean {
        val nome = txtNomeUsuario.text.toString().trim()
        val apelido = txtApelidoUsuario.text.toString().trim()
        val email = txtEmailUsuario.text.toString().trim()
        val senha = txtSenhaUsuario.text.toString().trim()
        val confirmarSenha = txtConfirmarSenhaUsuario.text.toString().trim()

        if (nome.isEmpty() || apelido.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (senha != confirmarSenha) {
            Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}