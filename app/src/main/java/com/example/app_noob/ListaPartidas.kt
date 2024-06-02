package com.example.app_noob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_noob.adapters.AtividadeAdapter
import com.example.app_noob.models.PartidaRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListaPartidas : AppCompatActivity() {

    lateinit var btnVoltarListaPartidas: ImageButton

    private lateinit var recyclerView: RecyclerView
    private lateinit var atividadeAdapter: AtividadeAdapter
    private lateinit var atividades: MutableList<PartidaRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_partidas)

        btnVoltarListaPartidas = findViewById(R.id.btnVoltarSelecionarJogo)

        btnVoltarListaPartidas.setOnClickListener(){
            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerViewAtividades)
        recyclerView.layoutManager = LinearLayoutManager(this)
        atividades = mutableListOf()
        atividadeAdapter = AtividadeAdapter(atividades)
        recyclerView.adapter = atividadeAdapter

        buscarAtividades()
        }


    private fun buscarAtividades() {

        val baseUrl = "https://api-noob.onrender.com" // Substituir pela URL base da API
        val atividadeApi = RetrofitClient.getClient(baseUrl).create(AtividadeApi::class.java)
        val call = atividadeApi.buscarAtividades()

        call.enqueue(object : Callback<List<PartidaRequest>> {
            override fun onResponse(call: Call<List<PartidaRequest>>, response: Response<List<PartidaRequest>>) {
                if (response.isSuccessful) {
                    atividades.clear()
                    response.body()?.let { atividades.addAll(it) }
                    atividadeAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@ListaPartidas, "Erro ao buscar atividades: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<PartidaRequest>>, t: Throwable) {
                Toast.makeText(this@ListaPartidas, "Erro na requisição: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}