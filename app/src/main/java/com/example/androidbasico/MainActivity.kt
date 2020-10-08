package com.example.androidbasico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.androidbasico.adapter.CarroAdapter
import com.example.androidbasico.model.Carro
import com.example.androidbasico.model.MarcaCarro
import com.example.androidbasico.util.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val ACTIVITY_ADICIONAR_CARRO = 2
        const val ACTIVITY_EDITAR_CARRO = 3
        const val MAIN_ACTIVITY_EDITAR_CARRO_EXTRA = "EDITAR_CARRRO"
        const val LISTA_CARROS = "saved_instance_car_list"
    }

    private var listCarros: MutableList<Carro> = mutableListOf()

    private val recyclerViewCarroAdapter =
        CarroAdapter(this, listCarros, this::onCarroClickAbrirActivityParaEdicaoListener)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(LISTA_CARROS, java.util.ArrayList<Carro>(listCarros))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        var lista = savedInstanceState.getParcelableArrayList<Carro>(LISTA_CARROS)!!
        listCarros.addAll(lista)
        recyclerViewCarroAdapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupAddButton()
        setupRecyclerView()
        setupDeleteSwipeOnRecyclerView()
    }

    private fun setupDeleteSwipeOnRecyclerView() {
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                recyclerViewCarroAdapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = recyclerViewCarroAdapter
        val layoutManager = GridLayoutManager(this, 1)
        recyclerView.layoutManager = layoutManager
    }

    private fun onCarroClickAbrirActivityParaEdicaoListener(carro: Carro) {
        val addAndEditUserActivity = Intent(this, AddAndEditUserActivity::class.java)
        addAndEditUserActivity.putExtra(MAIN_ACTIVITY_EDITAR_CARRO_EXTRA, carro)
        addAndEditUserActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivityForResult(addAndEditUserActivity, ACTIVITY_EDITAR_CARRO)
    }

    private fun setupAddButton() {
        floatButtonAdd.setOnClickListener {
            val addAndEditUserActivity = Intent(this, AddAndEditUserActivity::class.java)
            addAndEditUserActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivityForResult(addAndEditUserActivity, ACTIVITY_ADICIONAR_CARRO)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val carro = data?.getParcelableExtra<Carro>("CARRO");

        if (requestCode == ACTIVITY_ADICIONAR_CARRO && resultCode == RESULT_OK) {
            if (carro != null) {
                listCarros.add(carro)
                recyclerViewCarroAdapter.notifyDataSetChanged()
            }
        } else if (requestCode == ACTIVITY_EDITAR_CARRO && resultCode == RESULT_OK) {
            if (carro != null) {
                val index: Int = listCarros.indexOf(carro)
                listCarros[index] = carro
                recyclerViewCarroAdapter.notifyDataSetChanged()
            }
        }
    }
}