package id.ac.umn.mobileapp.explore.food

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputLayout
import id.ac.umn.mobileapp.R
import java.text.NumberFormat
import java.util.Locale

class FoodExploreAddtobagActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_explore_addtobag)

        val quantityArray = resources.getStringArray(R.array.quantity)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
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
        val harga = intent.getIntExtra("harga", 0) // Provide a default value if the key is not found
        val keterangan = intent.getStringExtra("keterangan")

        // Convert harga to a formatted string
        val formattedHarga = formatCurrency(harga)

        // Set data to views
        imageView.setImageResource(imageResource)
        tvNameFood.text = nameFood
        tvHarga.text = formattedHarga
        tvKeterangan.text = keterangan

        // Assuming you have a TextInputLayout with id tilSize and AutoCompleteTextView with id tieSize
        val tilQuantity: TextInputLayout = findViewById(R.id.tilQuantity)
        val tieQuantity: AutoCompleteTextView = findViewById(R.id.tieQuantity)

        // Create an ArrayAdapter using the string array and a default spinner layout
        val arrayAdapter = ArrayAdapter<String>(this, R.layout.dropdown_menu, quantityArray)

        // Specify the layout to use when the list of choices appears
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the AutoCompleteTextView
        tieQuantity.setAdapter(arrayAdapter)

        // Set the item click listener for handling the selected item
        tieQuantity.setOnItemClickListener { _, _, position, _ ->
            val selectedSize: String = quantityArray[position]
            // Handle the selected size as needed
        }
    }

    // Function to format currency
    private fun formatCurrency(amount: Int): String {
        val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        return format.format(amount.toLong())
    }
}
