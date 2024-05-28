package com.example.app_noob

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.app_noob.models.JogoRequest
import com.example.app_noob.models.JogoResponse
import com.example.app_noob.models.UsuarioRequest
import com.example.app_noob.models.UsuarioResponse
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

        btnVoltarCadastroJogos.setOnClickListener(){
            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
        }

        btnCadastrarJogos.setOnClickListener(){

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
        }


    }

    private fun cadastrarJogos(jogo: JogoRequest) {
        val baseUrl = "https://web-eg08riks0c18.up-de-fra1-k8s-1.apps.run-on-seenode.com" // Substitua pela URL base da sua API
        val jogoApi = RetrofitClient.getClient(baseUrl).create(JogoApi::class.java)
        val call = jogoApi.cadastrarJogo(jogo)

        call.enqueue(object : Callback<JogoResponse> {
            override fun onResponse(call: Call<JogoResponse>, response: Response<JogoResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    //Toast.makeText(this@CadastroUsuario, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this@CadastroJogos, response.body()!!.msg, Toast.LENGTH_SHORT).show()
                    //limparCampos()
                } else {
                    Toast.makeText(this@CadastroJogos, "Erro no cadastro: ${response.body()!!.msg}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JogoResponse>, t: Throwable) {
                Toast.makeText(this@CadastroJogos, "Erro na comunicação: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}