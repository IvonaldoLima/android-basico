package com.example.androidbasico

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidbasico.adapter.MarcaCarroAdapter
import com.example.androidbasico.model.Carro
import com.example.androidbasico.model.MarcaCarro
import kotlinx.android.synthetic.main.activity_add_and_edit_user.*
import java.util.*

class AddAndEditUserActivity : AppCompatActivity() {

    private var listaMarcasCarros: MutableList<MarcaCarro> = mutableListOf(
        MarcaCarro("Ferrari", R.drawable.ferrari),
        MarcaCarro("Porsche", R.drawable.porsche),
        MarcaCarro("Audi", R.drawable.audi),
        MarcaCarro("Bmw", R.drawable.bmw),
        MarcaCarro("Ford", R.drawable.ford),
        MarcaCarro("Jeep", R.drawable.jeep),
        MarcaCarro("Volkswagen", R.drawable.volkswagen),
        MarcaCarro("Renault", R.drawable.renault)
    )

    private lateinit var mMarcaCarroAdapter: MarcaCarroAdapter;
    private lateinit var car: Carro;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_and_edit_user)
        setupAdapter()
        setupSpinnerSelecionarModeloCaro()
        setupButtonSalvar()
        recuperarDadosDaTelaPrincipalParaEdicao()
    }

    private fun recuperarDadosDaTelaPrincipalParaEdicao() {
        val registroDoCarroQueSeraEditado =
            intent.getParcelableExtra<Carro>(MainActivity.MAIN_ACTIVITY_EDITAR_CARRO_EXTRA)
        if (registroDoCarroQueSeraEditado != null) {
            car = registroDoCarroQueSeraEditado
            carregarDadosNaTelaParaEdicao(car)
        }
    }

    private fun carregarDadosNaTelaParaEdicao(carro: Carro) {
        setItemSelecionadoNoSpinner(carro)
        editTextTextModelo.setText(carro.modelo)
        editTextNumberAno.setText(carro.anoFabricacao.toString())
        editTextTextCor.setText(carro.cor)
    }

    private fun setupAdapter() {
        mMarcaCarroAdapter = MarcaCarroAdapter(this, listaMarcasCarros)
    }

    private fun setupButtonSalvar() {
        buttonSalvar.setOnClickListener {
            if (validarFormulario()) {
                intent.putExtra("CARRO", gerarCarroComDadosDoFormulario())
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun validarFormulario(): Boolean {
        if (editTextTextModelo.text.isEmpty()) editTextTextModelo.error = "Campo deve ser preenchido"
        if (editTextNumberAno.text.isEmpty()) editTextNumberAno.error = "Campo deve ser preenchido"
        if (editTextTextCor.text.isEmpty()) editTextTextCor.error = "Campo deve ser preenchido"

        return !(editTextTextModelo.text.isEmpty() || editTextNumberAno.text.isEmpty() || editTextTextCor.text.isEmpty())
    }

    private fun gerarCarroComDadosDoFormulario(): Carro {
        var marcaCarro = mMarcaCarroAdapter.getSelectItem(spinnerMarcaCarros.selectedItemPosition)
        var modelo = editTextTextModelo.text.toString()
        var anoDeFabricacao = editTextNumberAno.text.toString().toInt()
        var cor = editTextTextCor.text.toString()

        return if (this::car.isInitialized) Carro(car.id, marcaCarro, modelo, anoDeFabricacao, cor)
        else Carro(UUID.randomUUID(), marcaCarro, modelo, anoDeFabricacao, cor)
    }

    private fun setItemSelecionadoNoSpinner(carro: Carro) {
        val marca: MarcaCarro =
            listaMarcasCarros.first() { it.nomeMarca == carro.marcaCarro.nomeMarca }
        var position: Int = mMarcaCarroAdapter.getPosition(marca)
        spinnerMarcaCarros.setSelection(position)
    }

    private fun setupSpinnerSelecionarModeloCaro() {
        spinnerMarcaCarros.adapter = mMarcaCarroAdapter
    }
}