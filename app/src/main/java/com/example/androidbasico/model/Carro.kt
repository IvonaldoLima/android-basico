package com.example.androidbasico.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Carro(
    val id: UUID,
    val marcaCarro: MarcaCarro,
    val modelo: String,
    val anoFabricacao: Int,
    val cor: String
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Carro
        if (id == other.id) return true
        return false
    }
}
