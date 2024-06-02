package com.example.app_noob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.app_noob.models.JogoRequest
import com.example.app_noob.models.JogoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroJogos : AppCompatActivity() {

    lateinit var btnVoltarCadastroJogos: ImageButton
    lateinit var btnCadastrarJogos: Button
    lateinit var txtTituloJogo: EditText
    lateinit var txtAnoJogo: EditText
    lateinit var txtIdadeJogo: EditText
    lateinit var txtDesignerJogo: EditText
    lateinit var txtArtistaJogo: EditText
    lateinit var txtEditoraJogo: EditText
    lateinit var txtVersaoDigitalJogo: EditText
    lateinit var txtCategoriaJogo: EditText
    lateinit var txtComponentesJogo: EditText
    lateinit var txtDescricaoJogo: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_jogos)

        btnVoltarCadastroJogos = findViewById(R.id.btnVoltarCadastroJogos)
        txtTituloJogo = findViewById(R.id.txtTituloJogo)
        txtAnoJogo = findViewById(R.id.txtAnoJogo)
        txtIdadeJogo = findViewById(R.id.txtIdadeJogo)
        txtDesignerJogo = findViewById(R.id.txtDesignerJogo)
        txtArtistaJogo = findViewById(R.id.txtArtistaJogo)
        txtEditoraJogo= findViewById(R.id.txtEditoraJogo)
        txtVersaoDigitalJogo= findViewById(R.id.txtVersaoDigitalJogo)
        txtCategoriaJogo= findViewById(R.id.txtCategoriaJogo)
        txtComponentesJogo=findViewById(R.id.txtComponentesJogo)
        txtDescricaoJogo= findViewById(R.id.txtDescricaoJogo)
        btnCadastrarJogos = findViewById(R.id.btnCadastrarJogos)

        // Obter o nome do usuário passado pela MainActivity
        val userName = intent.getStringExtra("USER_NAME")
        val userId = intent.getStringExtra("USER_ID")

        btnVoltarCadastroJogos.setOnClickListener(){
            val intent = Intent(this, MenuPrincipal::class.java).apply {
                putExtra("USER_NAME",userName)
                putExtra("USER_ID",userId)
            }
            startActivity(intent)
        }

        btnCadastrarJogos.setOnClickListener(){

            if(verificaCampo()){
                val titulo = txtTituloJogo.text.toString()
                val ano = txtAnoJogo.text.toString()
                val idade = txtIdadeJogo.text.toString()
                val designer = txtDesignerJogo.text.toString()
                val artista = txtArtistaJogo.text.toString()
                val editora = txtEditoraJogo.text.toString()
                val digital = txtVersaoDigitalJogo.text.toString()
                val categoria = txtCategoriaJogo.text.toString()
                val componentes = txtComponentesJogo.text.toString()
                val descricao = txtDescricaoJogo.text.toString()

                val jogo = JogoRequest(titulo, ano, idade, designer, artista, editora, digital, categoria, componentes, descricao)
                cadastrarJogos(jogo)

                val intent = Intent(this, MenuPrincipal::class.java).apply {
                    putExtra("USER_NAME",userName)
                    putExtra("USER_ID",userId)
                }
                startActivity(intent)
            }
        }
    }

    private fun cadastrarJogos(jogo: JogoRequest) {
        val baseUrl = "https://api-noob.onrender.com" // Substituir pela pela URL base da API
        val jogoApi = RetrofitClient.getClient(baseUrl).create(JogoApi::class.java)
        val call = jogoApi.cadastrarJogo(jogo)

        call.enqueue(object : Callback<JogoResponse> {
            override fun onResponse(call: Call<JogoResponse>, response: Response<JogoResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(this@CadastroJogos, response.body()!!.msg, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@CadastroJogos, "Erro no cadastro: ${response.body()!!.msg}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JogoResponse>, t: Throwable) {
                Toast.makeText(this@CadastroJogos, "Erro na comunicação: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun verificaCampo(): Boolean{
        val titulo = txtTituloJogo.text.toString().trim()

        if (titulo.isEmpty()){
            Toast.makeText(this, "Por favor preencher ao menos o título!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}