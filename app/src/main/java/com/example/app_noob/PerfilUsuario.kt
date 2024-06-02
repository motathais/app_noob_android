package com.example.app_noob

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.app_noob.models.UsuarioSearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class PerfilUsuario : AppCompatActivity() {

    lateinit var btnVoltarPerfilUsuario: ImageButton
    private lateinit var txtNomePerfil: EditText
    private lateinit var txtApelidoPerfil: EditText
    private lateinit var txtNascimentoPerfil: EditText
    private lateinit var txtEmailPerfil: EditText
    private lateinit var txtSenhaPerfil: EditText
    private lateinit var txtConfirmarSenhaPerfil: EditText
    private lateinit var btnAtualizarPerfil: Button
    private lateinit var btnEscolherFoto: Button
    private lateinit var fotoUsuario: ImageView
    private val PICK_IMAGE_REQUEST = 1
    private val CAMERA_REQUEST = 2
    private var imageUri: Uri? = null

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                fotoUsuario.setImageBitmap(bitmap)
                // Implementar a lógica de upload da imagem para o servidor aqui
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_usuario)

        btnVoltarPerfilUsuario = findViewById(R.id.btnVoltarSelecionarJogo)
        txtNomePerfil = findViewById(R.id.txtNomePerfil)
        txtApelidoPerfil = findViewById(R.id.txtApelidoPerfil)
        txtNascimentoPerfil = findViewById(R.id.txtNascimentoPerfil)
        txtEmailPerfil = findViewById(R.id.txtEmailPerfil)
        txtSenhaPerfil = findViewById(R.id.txtSenhaPerfil)
        txtConfirmarSenhaPerfil = findViewById(R.id.txtConfirmarSenhaPerfil)
        btnAtualizarPerfil = findViewById(R.id.btnAtualizarPerfil)
        btnEscolherFoto = findViewById(R.id.btnEscolherFoto)
        fotoUsuario = findViewById(R.id.fotoUsuario)

        // Obter o nome do usuário passado pela MainActivity
        val userName = intent.getStringExtra("USER_NAME")
        val userId = intent.getStringExtra("USER_ID")

        if (userId != null) {
            buscarUsuarios(userId)
        }

        btnVoltarPerfilUsuario.setOnClickListener() {
            val intent = Intent(this, MenuPrincipal::class.java).apply {
                putExtra("USER_NAME", userName)
                putExtra("USER_ID", userId)
            }
            startActivity(intent)
        }

        btnAtualizarPerfil.setOnClickListener() {
            if (userId != null) {
                atualizarUsuario(userId)
            }
        }

        btnEscolherFoto.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PICK_IMAGE_REQUEST)
            } else {
                pickImage()
            }
        }

    }

    private fun pickImage() {
        pickImageLauncher.launch("image/*")
    }



    private fun buscarUsuarios(userId: String) {
        val baseUrl = "https://api-noob.onrender.com" // Substituir pela URL base da API
        val usuarioApi = RetrofitClient.getClient(baseUrl).create(UsuarioApi::class.java)
        val call = usuarioApi.buscarUsuario(userId)

        call.enqueue(object : Callback<UsuarioSearch> {
            override fun onResponse(call: Call<UsuarioSearch>, response: Response<UsuarioSearch>) {
                if (response.isSuccessful) {
                    val userDetails = response.body()
                    Toast.makeText(
                        this@PerfilUsuario,
                        "Dados carregados com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()
                    userDetails?.let {
                        preencherCamposUsuario(it)
                    }
                } else {
                    Toast.makeText(
                        this@PerfilUsuario,
                        "Falha ao carregar dados!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<UsuarioSearch>, t: Throwable) {
                Toast.makeText(this@PerfilUsuario, "Falha de requisição!", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun preencherCamposUsuario(usuarioSearch: UsuarioSearch) {
        txtNomePerfil.setText(usuarioSearch.nome)
        txtApelidoPerfil.setText(usuarioSearch.apelido)
        val dataFormatada = formatarData(usuarioSearch.nascimento)
        txtNascimentoPerfil.setText(dataFormatada)
        txtEmailPerfil.setText(usuarioSearch.email)

    }

    private fun atualizarUsuario(userId: String) {
        val nome = txtNomePerfil.text.toString()
        val apelido = txtApelidoPerfil.text.toString()
        val nascimento = txtNascimentoPerfil.text.toString()
        val email = txtEmailPerfil.text.toString()
        val novaSenha = txtSenhaPerfil.text.toString()
        val confirmarSenha = txtConfirmarSenhaPerfil.text.toString()

        if (novaSenha.isNotEmpty() && novaSenha != confirmarSenha) {
            Toast.makeText(this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show()
            return
        }

        val usuarioAtualizado = UsuarioSearch(
            id = userId,
            nome = nome,
            apelido = apelido,
            nascimento = nascimento,
            email = email,
            senha = if (novaSenha.isNotEmpty()) novaSenha else null
        )

        val baseUrl = "https://api-noob.onrender.com" // Substitua pela URL base da sua API
        val usuarioApi = RetrofitClient.getClient(baseUrl).create(UsuarioApi::class.java)
        val call = usuarioApi.atualizarUsuario(userId, usuarioAtualizado)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@PerfilUsuario,
                        "Dados atualizados com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@PerfilUsuario,
                        "Falha ao atualizar dados!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@PerfilUsuario, "Falha de requisição!", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun formatarData(dataOriginal: String): String {
        return try {
            Log.d("PerfilUsuario", "Formatando data: $dataOriginal")
            // Extrair os 10 primeiros caracteres da data
            val dataCurta = dataOriginal.substring(0, 10)
            // Define o formato de entrada
            val formatoEntrada = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            // Converte a string para um objeto Date
            val date: Date? = formatoEntrada.parse(dataCurta)
            // Define o formato de saída
            val formatoSaida = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            // Retorna a data no formato desejado
            if (date != null) {
                formatoSaida.format(date)
            } else {
                dataOriginal
            }
        } catch (e: Exception) {
            Log.e("PerfilUsuario", "Erro ao formatar data: ${e.message}")
            // Se houver um erro, retorna a data original
            dataOriginal
        }
    }
}
