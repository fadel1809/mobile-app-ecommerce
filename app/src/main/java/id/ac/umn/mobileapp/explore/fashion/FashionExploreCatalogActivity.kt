package id.ac.umn.mobileapp.explore.fashion

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import id.ac.umn.mobileapp.R
import id.ac.umn.mobileapp.bag.BagFragment
import id.ac.umn.mobileapp.bag.fashion.FashionBagFragment
import id.ac.umn.mobileapp.explore.ExploreFragment
import id.ac.umn.mobileapp.profile.LoginFragment
import id.ac.umn.mobileapp.wishlist.Wishlist
import java.text.NumberFormat
import java.util.Locale

class FashionExploreCatalogActivity : AppCompatActivity() {
    data class Bag(
        val user:String, val productName: String,
        val harga: Long,
        val selectedSize: String, val quantity:String, val image:String, val category: String
    )

    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        val database = FirebaseDatabase.getInstance()
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
        val tvNameFashion: TextView = findViewById(R.id.tvNameFoodCheckout)
        val tvHarga: TextView = findViewById(R.id.tvHargaCheckout)
        val tvKeterangan: TextView = findViewById(R.id.tvKeteranganCheckout)
        val btnWishlist : MaterialButton = findViewById(R.id.btnWishlist)
        val btnBag : MaterialButton = findViewById(R.id.btnAddtobag)
        val btnChheckOut: MaterialButton = findViewById(R.id.btnCheckout)
        // Retrieve data from the intent
        val imageResourceName = intent.getStringExtra("imageResource")
        val nameFashion = intent.getStringExtra("nameFashion")
        val harga = intent.getLongExtra("harga", 0)
        val keterangan = intent.getStringExtra("keterangan")
        val sharedPreferences = getSharedPreferences("user_data",Context.MODE_PRIVATE)
        val id = sharedPreferences.getString("id",null)


        // Convert harga to a formatted string
        val formattedHarga = formatCurrency(harga)

        // Get the resource ID for the image
        val resourceId = resources.getIdentifier(imageResourceName, "drawable", packageName)

        // Set data to views
        imageView.setImageResource(resourceId)
        tvNameFashion.text = nameFashion
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
        arrayAdapterQuantity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

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
        btnWishlist.setOnClickListener{
            val db = database.getReference("wishlist")
            val wislisAdd = db.push()
            val wislis = Wishlist(imageResourceName.toString(),nameFashion.toString(),harga, " ","")
            if (wislis!=null){
                wislisAdd.setValue(wislis).addOnSuccessListener{
                    Toast.makeText(this@FashionExploreCatalogActivity, "Added to Wishlist", Toast.LENGTH_SHORT).show()

                }.addOnFailureListener{
                    Toast.makeText(this@FashionExploreCatalogActivity, "Adding Failed", Toast.LENGTH_SHORT).show()

                }
            }
        }
        btnBag.setOnClickListener{
            if(id != null){
                val bagTbl = database.getReference("bag")
                val bagAdd = bagTbl.push()

                val selectedSize = tieSize.text.toString()
                val selectedQuantity = tieQuantity.text.toString()

                val bag = nameFashion?.let {
                    Bag(id, it, harga, selectedSize, selectedQuantity, imageResourceName.toString(),"fashion")
                }

                if (bag != null) {
                    bagAdd.setValue(bag).addOnSuccessListener{
                        Toast.makeText(this@FashionExploreCatalogActivity, "Added To Bag", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        Toast.makeText(this@FashionExploreCatalogActivity, "Added Failed", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Handle the case where bag is null
                    navigateToLoginFragment()
                }
            } else {
                Toast.makeText(this@FashionExploreCatalogActivity, "Silahkan Login Terlebih Dahulu", Toast.LENGTH_SHORT).show()

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
        val myProfileFragment = ExploreFragment()
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
            val newBagFragment = BagFragment()
            val transaction: FragmentTransaction = bagFragment.childFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, newBagFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        } else {
            // Log the error details
            Log.e("FashionExploreCatalogActivity", "Error navigating to BagFragment. BagFragment not found.")
            Toast.makeText(this@FashionExploreCatalogActivity, "Error navigating to BagFragment", Toast.LENGTH_SHORT).show()
        }
    }

}