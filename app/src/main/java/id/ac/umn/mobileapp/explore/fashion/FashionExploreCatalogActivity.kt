package id.ac.umn.mobileapp.explore.fashion

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import id.ac.umn.mobileapp.R
import java.text.NumberFormat
import java.util.Locale

class FashionExploreCatalogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fashion_explore_catalog)

        val sizeArray = resources.getStringArray(R.array.size)
        val quantityArray = resources.getStringArray(R.array.quantity)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Add navigation icon
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Add onClickListener for the navigation icon
        toolbar.setNavigationOnClickListener {
            // Navigate back to the previous fragment or perform appropriate operation
            onBackPressed()
        }

        val imageView: ImageView = findViewById(R.id.imageViewCheckout)
        val tvNameFood: TextView = findViewById(R.id.tvNameFoodCheckout)
        val tvHarga: TextView = findViewById(R.id.tvHargaCheckout)
        val tvKeterangan: TextView = findViewById(R.id.tvKeteranganCheckout)

        // Retrieve data from the intent
        val imageResource = intent.getIntExtra("imageResource", 0)
        val nameFood = intent.getStringExtra("nameFood")
        val harga = intent.getIntExtra("harga", 0)
        val keterangan = intent.getStringExtra("keterangan")

        // Convert harga to a formatted string
        val formattedHarga = formatCurrency(harga)

        // Set data to views
        imageView.setImageResource(imageResource)
        tvNameFood.text = nameFood
        tvHarga.text = formattedHarga
        tvKeterangan.text = keterangan

        val tilQuantity: TextInputLayout = findViewById(R.id.tilQuantity)
        val tieQuantity: AutoCompleteTextView = findViewById(R.id.tieQuantity)
        val tilSize: TextInputLayout = findViewById(R.id.tilSize)
        val tieSize: AutoCompleteTextView = findViewById(R.id.tieSize)

        // Create an ArrayAdapter using the string array and a default spinner layout
        val arrayAdapterSize = ArrayAdapter<String>(this, R.layout.dropdown_menu, sizeArray)
        val arrayAdapterQuantity = ArrayAdapter<String>(this, R.layout.dropdown_menu, quantityArray)

        // Specify the layout to use when the list of choices appears
        arrayAdapterSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        arrayAdapterSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the AutoCompleteTextView
        tieSize.setAdapter(arrayAdapterSize)
        tieQuantity.setAdapter(arrayAdapterQuantity)

        // Set the item click listener for handling the selected item
        tieSize.setOnItemClickListener { _, _, position, _ ->
            val selectedSize: String = sizeArray[position]
            // Handle the selected size as needed
        }

        tieQuantity.setOnItemClickListener { _, _, position, _ ->
            val selectedQuantity: String = quantityArray[position]
            // Handle the selected size as needed
        }
    }

    // Function to format currency
    private fun formatCurrency(amount: Int): String {
        val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        return format.format(amount.toLong())
    }
}
