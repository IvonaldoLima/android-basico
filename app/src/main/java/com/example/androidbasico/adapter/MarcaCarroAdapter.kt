package com.example.androidbasico.adapter

import android.content.Context
import android.view.LayoutInflater.*
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.androidbasico.R
import com.example.androidbasico.model.Carro
import com.example.androidbasico.model.MarcaCarro
import kotlinx.android.synthetic.main.activity_add_and_edit_user.*
import kotlinx.android.synthetic.main.custom_spinner_row.view.*


class MarcaCarroAdapter(
    context: Context,
    private val listMarcasCarros: MutableList<MarcaCarro>
) : ArrayAdapter<String>(context, R.layout.custom_spinner_row) {

    override fun getCount(): Int = listMarcasCarros.size

    fun getSelectItem(position: Int): MarcaCarro{
        return listMarcasCarros[position]
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View =
        getView(position, convertView, parent)

    fun getPosition(item: MarcaCarro): Int {
        return listMarcasCarros.indexOf(item)
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var retView: View
        var mViewHolder: ViewHolder;

        if (convertView == null) {
            retView = from(parent.context).inflate(R.layout.custom_spinner_row, parent, false)
            mViewHolder = ViewHolder(retView)
            mViewHolder.mFlag?.setImageResource(listMarcasCarros[position].imagemMarca)
            mViewHolder.mName?.text = (listMarcasCarros[position].nomeMarca);
            retView.tag = mViewHolder
        } else {
            mViewHolder = convertView.tag as ViewHolder
            retView = convertView
        }

        mViewHolder.mFlag?.setImageResource(listMarcasCarros[position].imagemMarca)
        mViewHolder.mName?.text = (listMarcasCarros[position].nomeMarca);

        return retView
    }

    class ViewHolder(itemView: View) {
        val mFlag: ImageView? = itemView.ivFlag
        var mName: TextView? = itemView.tvName
    }
}