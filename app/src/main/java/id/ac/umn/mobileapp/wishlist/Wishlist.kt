package id.ac.umn.mobileapp.wishlist

import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView

data class Wishlist(
    val imageResourceName: String,
    val tvNameWishlist: String,
    val tvHarga: Long,
    val quantity: String,
    val selectedSize: String
)
