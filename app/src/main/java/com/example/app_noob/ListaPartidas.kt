package com.example.app_noob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_noob.adapters.AtividadeAdapter
import com.example.app_noob.models.PartidaRequest
import com.example.app_noob.models.PartidaSearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListaPartidas : AppCompatActivity() {

    lateinit var btnVoltarListaPartidas: ImageButton

    private lateinit var recyclerView: RecyclerView
    private lateinit var atividadeAdapter: AtividadeAdapter
    private val atividades: MutableList<PartidaSearch> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_partidas)

        btnVoltarListaPartidas = findViewById(R.id.btnVoltarSelecionarJogo)

        btnVoltarListaPartidas.setOnClickListener {
            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerViewAtividades)
        recyclerView.layoutManager = LinearLayoutManager(this)

        atividadeAdapter = AtividadeAdapter(atividades) { atividade ->
            mostrarDialogoDeletarPartida(atividade)
        }
        recyclerView.adapter = atividadeAdapter

        carregarAtividades()
    }

    private fun carregarAtividades() {
        val baseUrl = "https://api-noob.onrender.com" // Substituir pela URL base da API
        val atividadeApi = RetrofitClient.getClient(baseUrl).create(AtividadeApi::class.java)
        val call = atividadeApi.buscarAtividades()

        call.enqueue(object : Callback<List<PartidaSearch>> {
            override fun onResponse(
                call: Call<List<PartidaSearch>>,
                response: Response<List<PartidaSearch>>
            ) {
                if (response.isSuccessful) {
                    atividades.clear()
                    atividades.addAll(response.body() ?: emptyList())
                    atividadeAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        this@ListaPartidas,
                        "Erro ao carregar as atividades: ${response.errorBody()?.string()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<PartidaSearch>>, t: Throwable) {
                Toast.makeText(
                    this@ListaPartidas,
                    "Erro na requisição: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("ListaPartidas", "Erro na chamada à API", t)
            }
        })
    }

    private fun mostrarDialogoDeletarPartida(atividade: PartidaSearch) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Deseja deletar esta partida?")
            .setPositiveButton("Sim") { _, _ ->
                deletarPartida(atividade)
            }
            .setNegativeButton("Não", null)
            .show()
    }

    private fun deletarPartida(atividade: PartidaSearch) {
        val id = atividade.id
        val baseUrl = "https://api-noob.onrender.com"
        val atividadeApi = RetrofitClient.getClient(baseUrl).create(AtividadeApi::class.java)

        if (id != null) {
            val call = atividadeApi.deletarAtividade(id)

            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@ListaPartidas,
                            "Partida deletada com sucesso!",
                            Toast.LENGTH_SHORT
                        ).show()
                        atividades.remove(atividade)
                        atividadeAdapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(
                            this@ListaPartidas,
                            "Erro ao deletar a partida",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@ListaPartidas, "Erro na requisição: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this@ListaPartidas, "O id é nulo", Toast.LENGTH_SHORT)
                .show()
        }
    }
}