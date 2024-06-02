package com.example.app_noob.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_noob.R
import com.example.app_noob.models.PartidaRequest

/*class AtividadeAdapter(private val atividades: List<PartidaRequest>) : RecyclerView.Adapter<AtividadeAdapter.AtividadeViewHolder>() {

    class AtividadeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvJogoTitulo: TextView = itemView.findViewById(R.id.tvJogoTitulo)
        val tvVencedor: TextView = itemView.findViewById(R.id.tvVencedor)
        val tvDuracao: TextView = itemView.findViewById(R.id.tvDuracao)
        val tvUsuarios: TextView = itemView.findViewById(R.id.tvUsuarios)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AtividadeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_atividade, parent, false)
        return AtividadeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AtividadeViewHolder, position: Int) {
        val atividade = atividades[position]
        holder.tvJogoTitulo.text = atividade.jogo.joinToString { it.titulo }
        holder.tvVencedor.text = "Vencedor: ${atividade.vencedor.nome}"
        holder.tvDuracao.text = "Duração: ${atividade.duracao}"
        holder.tvUsuarios.text = "Participantes: ${atividade.usuarios.joinToString { it.nome }}"
    }

    override fun getItemCount(): Int = atividades.size
}*/

class AtividadeAdapter(private val atividades: List<PartidaRequest>) : RecyclerView.Adapter<AtividadeAdapter.AtividadeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AtividadeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_atividade, parent, false)
        return AtividadeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AtividadeViewHolder, position: Int) {
        val atividade = atividades[position]
        holder.bind(atividade)
    }

    override fun getItemCount(): Int = atividades.size

    class AtividadeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val usuariosTextView: TextView = itemView.findViewById(R.id.tvUsuarios)
        private val jogoTextView: TextView = itemView.findViewById(R.id.tvJogoTitulo)
        private val vencedorTextView: TextView = itemView.findViewById(R.id.tvVencedor)
        private val duracaoTextView: TextView = itemView.findViewById(R.id.tvDuracao)

        fun bind(atividade: PartidaRequest) {
            usuariosTextView.text = atividade.usuarios.joinToString(", ") { it.nome }
            jogoTextView.text = atividade.jogo.joinToString(", ") { it.titulo }
            vencedorTextView.text = if (atividade.vencedor.isNotEmpty()) atividade.vencedor[0].nome else "N/A"
            duracaoTextView.text = atividade.duracao
        }
    }
}