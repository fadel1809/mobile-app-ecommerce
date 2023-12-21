package id.ac.umn.mobileapp.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.ac.umn.mobileapp.R
import id.ac.umn.mobileapp.checkout.Checkout
import java.text.NumberFormat
import java.util.Locale

class OrderActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderAdapter
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Tambahkan ikon navigasi
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Tambahkan onClickListener untuk ikon navigasi
        toolbar.setNavigationOnClickListener {
            // Kembali ke fragment sebelumnya atau lakukan operasi yang sesuai
            onBackPressed()
        }
        recyclerView = findViewById(R.id.recyclerView)
        val layoutManager = GridLayoutManager(this, 1)
        recyclerView.layoutManager = layoutManager

        // Initialize adapter outside the fetchUserData method
        adapter = OrderAdapter(emptyList())
        recyclerView.adapter = adapter

        databaseReference = FirebaseDatabase.getInstance().getReference("checkout")
        fetchUserData()
    }

    private fun fetchUserData() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tvTotal: TextView? = findViewById(R.id.tvTotal)
                val orderList = mutableListOf<Order>() // Assuming FashionBag is the correct model class
                var total: Long = 0

                for (userSnapshot in snapshot.children) {
                    val category = userSnapshot.child("category").getValue(String::class.java) ?: ""

                    // Check if the category is either "fashion" or "foods"
                    if (category == "fashion" || category == "foods") {
                        val image = userSnapshot.child("image").getValue(String::class.java) ?: ""
                        val name = userSnapshot.child("productName").getValue(String::class.java) ?: ""
                        val harga = userSnapshot.child("harga").getValue(Long::class.java) ?: 0
                        val size = userSnapshot.child("selectedSize").getValue(String::class.java) ?: ""
                        val quantity = userSnapshot.child("quantity").getValue(String::class.java) ?: ""
                        val qtyInt = try {
                            quantity.toInt()
                        } catch (e: NumberFormatException) {
                            0
                        }
                        val hargaPerItem: Long = harga * qtyInt
                        total += hargaPerItem

                        val order = Order(image, name, harga, size, quantity)
                        orderList.add(order)
                    }
                }

                tvTotal?.text = formatCurrency(total)

                // Update data in the adapter
                adapter.dataOrder = orderList
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
            }
        })
    }

    private fun formatCurrency(amount: Long): String {
        val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        val symbols = (format as java.text.DecimalFormat).decimalFormatSymbols
        symbols.currencySymbol = "Rp. "
        (format as java.text.DecimalFormat).decimalFormatSymbols = symbols
        return format.format(amount)
    }
}