package com.example.app_noob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.app_noob.models.UsuarioSearch
import org.bson.types.ObjectId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilUsuario : AppCompatActivity() {

    lateinit var btnVoltarPerfilUsuario: ImageButton
    private lateinit var txtNomePerfil: EditText
    private lateinit var txtApelidoPerfil: EditText
    private lateinit var txtNascimentoPerfil: EditText
    private lateinit var txtEmailPerfil: EditText
    private lateinit var txtSenhaPerfil: EditText
    private lateinit var txtConfirmarSenhaPerfil: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_usuario)

        btnVoltarPerfilUsuario= findViewById(R.id.btnVoltarPerfilUsuario)
        txtNomePerfil = findViewById(R.id.txtNomePerfil)
        txtApelidoPerfil = findViewById(R.id.txtApelidoPerfil)
        txtNascimentoPerfil = findViewById(R.id.txtNascimentoPerfil)
        txtEmailPerfil = findViewById(R.id.txtEmailPerfil)
        txtSenhaPerfil = findViewById(R.id.txtSenhaPerfil)
        txtConfirmarSenhaPerfil = findViewById(R.id.txtConfirmarSenhaPerfil)

       // val userId = intent.getStringExtra("USER_ID")

        // ID como String
        //val userIdString = "6647f02dd54f5b9768a1b1b9"

        val userId = "6647f02dd54f5b9768a1b1b9"

        // Convertendo a String para ObjectId
        //val userId = ObjectId(userIdString)

        if (userId != null) {
            buscarUsuarios(userId)
        }
        else{
            Toast.makeText(this@PerfilUsuario, "Não veio nada!", Toast.LENGTH_SHORT).show()
        }

        btnVoltarPerfilUsuario.setOnClickListener(){
            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
        }
    }

    private fun buscarUsuarios(userId:String){
        val baseUrl = "https://web-eg08riks0c18.up-de-fra1-k8s-1.apps.run-on-seenode.com" // Substitua pela URL base da sua API
        val usuarioApi = RetrofitClient.getClient(baseUrl).create(UsuarioApi::class.java)
        val call = usuarioApi.buscarUsuario(userId)

        call.enqueue(object : Callback<UsuarioSearch> {
            override fun onResponse(call: Call<UsuarioSearch>, response: Response<UsuarioSearch>) {
                if (response.isSuccessful) {
                    val userDetails = response.body()

                    Toast.makeText(this@PerfilUsuario, "Dados carregados com sucesso!", Toast.LENGTH_SHORT).show()

                    userDetails?.let {
                        preencherCamposUsuario(it)

                    }
                } else {
                    // Tratar erro ao buscar detalhes do usuário
                    //Log.e("ProfileActivity", "Erro ao buscar detalhes do usuário: ${response.message()}")
                    Toast.makeText(this@PerfilUsuario, "Falha ao carregar dados!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UsuarioSearch>, t: Throwable) {
                // Tratar falha de requisição
               // Log.e("ProfileActivity", "Falha de requisição: ${t.message}")
                Toast.makeText(this@PerfilUsuario, "Falha de requisição!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun preencherCamposUsuario(usuarioSearch: UsuarioSearch) {
        txtNomePerfil.setText(usuarioSearch.nome)
        txtApelidoPerfil.setText(usuarioSearch.apelido)
        txtNascimentoPerfil.setText(usuarioSearch.nascimento)
        txtEmailPerfil.setText(usuarioSearch.email)
        txtSenhaPerfil.setText(usuarioSearch.senha)
        txtConfirmarSenhaPerfil.setText(usuarioSearch.senha)

        // Preencher txtSenhaPerfil e txtConfirmarSenhaPerfil se necessário
    }

    }
