package com.example.androidbasico.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class MarcaCarro(val nomeMarca: String, val imagemMarca:Int) : Parcelable {
}