package id.ac.umn.mobileapp.explore.food

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import id.ac.umn.mobileapp.R

class FoodExploreAddtobagActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_explore_addtobag)

        val imageView: ImageView = findViewById(R.id.imageViewCheckout)
        val tvNameFood: TextView = findViewById(R.id.tvNameFoodCheckout)
        val tvHarga: TextView = findViewById(R.id.tvHargaCheckout)
        val tvKeterangan: TextView = findViewById(R.id.tvKeteranganCheckout)

        // Retrieve data from the intent
        val imageResource = intent.getIntExtra("imageResource", 0)
        val nameFood = intent.getStringExtra("nameFood")
        val harga = intent.getStringExtra("harga")
        val keterangan = intent.getStringExtra("keterangan")

        // Set data to views
        imageView.setImageResource(imageResource)
        tvNameFood.text = nameFood
        tvHarga.text = harga
        tvKeterangan.text = keterangan
    }
}
