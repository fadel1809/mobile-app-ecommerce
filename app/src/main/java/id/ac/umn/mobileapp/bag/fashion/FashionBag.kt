package id.ac.umn.mobileapp.bag.fashion

import android.widget.AutoCompleteTextView

data class FashionBag(
    val imageResourceName: String,
    val tvNameFashion: String,
    val tvHarga: Long,
    val selectedSize: String,
    val tvQuantity : String
)