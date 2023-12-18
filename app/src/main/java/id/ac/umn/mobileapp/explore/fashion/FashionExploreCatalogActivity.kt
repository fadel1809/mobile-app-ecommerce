package id.ac.umn.mobileapp.explore.fashion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import id.ac.umn.mobileapp.R

class FashionExploreCatalogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fashion_explore_catalog)
        val imageView: ImageView = findViewById(R.id.imageViewCheckout)
        val tvNameFood: TextView = findViewById(R.id.tvNameFoodCheckout)
        val tvHarga: TextView = findViewById(R.id.tvHargaCheckout)
        val tvKeterangan: TextView = findViewById(R.id.tvKeteranganCheckout)

        // Retrieve data from the intent
        val imageResource = intent.getIntExtra("imageResource", 0)
        val nameFood = intent.getStringExtra("nameFood")
        val harga = intent.getStringExtra("harga")

        // Set data to views
        imageView.setImageResource(imageResource)
        tvNameFood.text = nameFood
        tvHarga.text = harga
    }
}