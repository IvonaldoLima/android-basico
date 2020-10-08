package com.example.androidbasico.adapter

import android.content.Context
import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidbasico.R
import com.example.androidbasico.model.Carro
import kotlinx.android.synthetic.main.item_carro.view.*

class CarroAdapter(
    private val context: Context,
    private val carros: MutableList<Carro>,
    private val callback: (Carro) -> Unit
) : RecyclerView.Adapter<CarroAdapter.VH>() {

    private val imagemCarros: TypedArray by lazy {
        context.resources.obtainTypedArray(R.array.carros)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_carro, parent, false)
        val vh = VH(v)
        vh.itemView.setOnClickListener {
            val carro = carros[vh.adapterPosition]
            callback(carro)
        }
        return vh
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val carro: Carro = carros[position]
        holder.imagemCarro?.setImageResource(carro.marcaCarro.imagemMarca)
        holder.marcaCarro.text = carro.marcaCarro.nomeMarca
        holder.nomeModelo.text = carro.modelo
        holder.anoFabricacao.text = carro.anoFabricacao.toString()
        holder.cor.text = carro.cor
    }

    override fun getItemCount(): Int = carros.size

    fun removeAt(adapterPosition: Int) {
        carros.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagemCarro: ImageView = itemView.imageViewCarro
        val marcaCarro: TextView = itemView.textViewMarcaCarro
        val nomeModelo: TextView = itemView.textViewModelo
        val anoFabricacao: TextView = itemView.textViewAnoFabricacao
        val cor: TextView = itemView.textViewCor
    }
}