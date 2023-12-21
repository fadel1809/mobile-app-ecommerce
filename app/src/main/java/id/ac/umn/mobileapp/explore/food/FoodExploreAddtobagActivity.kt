package id.ac.umn.mobileapp.explore.food

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import id.ac.umn.mobileapp.R
import id.ac.umn.mobileapp.bag.BagFragment
import id.ac.umn.mobileapp.bag.fashion.FashionBagFragment
import id.ac.umn.mobileapp.bag.food.FoodBagFragment
import id.ac.umn.mobileapp.profile.LoginFragment
import java.text.NumberFormat
import java.util.Locale

class FoodExploreAddtobagActivity : AppCompatActivity() {
    data class Bag(
        val user: String,
        val productName: String,
        val harga: Long,
        val quantity: String,
        val image: String,
        val category: String
    )

    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        val database = FirebaseDatabase.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_explore_addtobag)

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
        val btnWishlist : MaterialButton = findViewById(R.id.btnWishlist)
        val btnBag : MaterialButton = findViewById(R.id.btnAddtobag)
        val btnCheckOut: MaterialButton = findViewById(R.id.btnCheckout)
        // Retrieve data from the intent
        val imageResourceName = intent.getStringExtra("imageResource")
        val nameFood = intent.getStringExtra("nameFood")
        val harga = intent.getLongExtra("harga", 0)
        val keterangan = intent.getStringExtra("keterangan")
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val id = sharedPreferences.getString("id",null)

        // Convert harga to a formatted string
        val formattedHarga = formatCurrency(harga)

        // Get the resource ID for the image
        val resourceId = resources.getIdentifier(imageResourceName, "drawable", packageName)

        // Set data to views
        imageView.setImageResource(resourceId)
        tvNameFood.text = nameFood
        tvHarga.text = formattedHarga
        tvKeterangan.text = keterangan

        val tilQuantity: TextInputLayout = findViewById(R.id.tilQuantity)
        val tieQuantity: AutoCompleteTextView = findViewById(R.id.tieQuantity)

        // Create an ArrayAdapter using the string array and a default spinner layout
        val arrayAdapterQuantity = ArrayAdapter<String>(this, R.layout.dropdown_menu, quantityArray)

        // Specify the layout to use when the list of choices appears
        arrayAdapterQuantity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the AutoCompleteTextView
        tieQuantity.setAdapter(arrayAdapterQuantity)

        tieQuantity.setOnItemClickListener { _, _, position, _ ->
            val selectedQuantity: String = quantityArray[position]
            // Handle the selected size as needed
        }

        btnBag.setOnClickListener{
            if(id != null){
                val bagTbl = database.getReference("bag")
                val bagAdd = bagTbl.push()

                val selectedQuantity = tieQuantity.text.toString()

                val bag = nameFood?.let {
                    Bag(id, it, harga, selectedQuantity, imageResourceName.toString(), "foods")
                }

                if (bag != null) {
                    bagAdd.setValue(bag).addOnSuccessListener{
                        Toast.makeText(this@FoodExploreAddtobagActivity, "Added to Bag", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        Toast.makeText(this@FoodExploreAddtobagActivity, "Added Failed", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Handle the case where bag is null
                    Toast.makeText(this@FoodExploreAddtobagActivity, "Error Try Again", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@FoodExploreAddtobagActivity, "Silahkan Login Terlebih Dahulu", Toast.LENGTH_SHORT).show()

            }
        }
    }

    // Function to format currency
    private fun formatCurrency(amount: Long): String {
        val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        val symbols = (format as java.text.DecimalFormat).decimalFormatSymbols
        symbols.currencySymbol = "Rp. "
        (format as java.text.DecimalFormat).decimalFormatSymbols = symbols
        return format.format(amount)

    }

    private fun navigateToLoginFragment(){
        val myProfileFragment = LoginFragment()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, myProfileFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun navigateToBagFragment() {
        // Check if the frame_container contains BagFragment
        val bagFragment = supportFragmentManager.findFragmentById(R.id.frame_container)

        if (bagFragment is BagFragment) {
            // BagFragment is present, replace its content with BagFragment
            val newBagFragment = FoodBagFragment()
            val transaction: FragmentTransaction = bagFragment.childFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, newBagFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        } else {
            // Log the error details
            Log.e("FashionExploreCatalogActivity", "Error navigating to BagFragment. BagFragment not found.")
            Toast.makeText(this@FoodExploreAddtobagActivity, "Error navigating to BagFragment", Toast.LENGTH_SHORT).show()
        }
    }

}