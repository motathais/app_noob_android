package com.example.app_noob.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_noob.R
import com.example.app_noob.models.PartidaSearch


class AtividadeAdapter(
    private val atividades: List<PartidaSearch>,
    private val onLongClick: (PartidaSearch) -> Unit
) : RecyclerView.Adapter<AtividadeAdapter.AtividadeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AtividadeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_atividade, parent, false)
        return AtividadeViewHolder(view, onLongClick)
    }

    override fun onBindViewHolder(holder: AtividadeViewHolder, position: Int) {
        val atividade = atividades[position]
        holder.bind(atividade)
    }

    override fun getItemCount(): Int = atividades.size

    class AtividadeViewHolder(
        itemView: View,
        private val onLongClick: (PartidaSearch) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val idTextView: TextView = itemView.findViewById(R.id.tvJogoId)
        private val usuariosTextView: TextView = itemView.findViewById(R.id.tvUsuarios)
        private val jogoTextView: TextView = itemView.findViewById(R.id.tvJogoTitulo)
        private val vencedorTextView: TextView = itemView.findViewById(R.id.tvVencedor)
        private val duracaoTextView: TextView = itemView.findViewById(R.id.tvDuracao)

        fun bind(atividade: PartidaSearch) {
            idTextView.text = atividade.id
            usuariosTextView.text = atividade.usuarios.joinToString(", ") { it.nome }
            jogoTextView.text = atividade.jogo.joinToString(", ") { it.titulo }
            vencedorTextView.text = if (atividade.vencedor.isNotEmpty()) atividade.vencedor[0].nome else "N/A"
            duracaoTextView.text = atividade.duracao

            itemView.setOnLongClickListener {
                onLongClick(atividade)
                true
            }
        }
    }
}