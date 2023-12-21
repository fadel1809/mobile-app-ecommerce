package id.ac.umn.mobileapp.bag.food

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.ac.umn.mobileapp.R
import id.ac.umn.mobileapp.checkout.CheckoutActivity
import java.text.NumberFormat
import java.util.Locale

class FoodBagFragment : Fragment() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FoodBagAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_food_bag, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        val layoutManager = GridLayoutManager(requireContext(), 1)
        recyclerView.layoutManager = layoutManager

        // Initialize adapter outside the fetchUserData method
        adapter = FoodBagAdapter(emptyList())
        recyclerView.adapter = adapter

        databaseReference = FirebaseDatabase.getInstance().getReference("bag")

        // Fetch data and update adapter
        fetchUserData()

        val btnCO: MaterialButton? = view?.findViewById(R.id.btnCheckout)
        btnCO?.setOnClickListener {
            val intent = Intent(requireContext(), CheckoutActivity::class.java)

            // Pass tvTotal to CheckoutActivity
            val tvTotal: TextView? = view?.findViewById(R.id.tvTotal)
            val totalAmount = tvTotal?.text?.toString() ?: "0"
            intent.putExtra("TOTAL_AMOUNT", totalAmount)

            // Pass data from the snapshot to CheckoutActivity
            val foodList = adapter.dataBagFood // Assuming fashionList is the list containing snapshot data
            val dataBundle = Bundle()

            for (food in foodList) {
                val itemBundle = Bundle()
                itemBundle.putString("image", food.imageResourceName)
                itemBundle.putString("productName", food.tvNameFood)
                itemBundle.putLong("harga", food.tvHarga)
                itemBundle.putString("quantity", food.quantity)

                // Use a unique key for each item, e.g., "ITEM_1", "ITEM_2", etc.
                dataBundle.putBundle("ITEM_${foodList.indexOf(food) + 1}", itemBundle)
            }

            intent.putExtra("DATA_BUNDLE", dataBundle)
            startActivity(intent)
        }

        return view
    }

    private fun fetchUserData() {
        databaseReference.orderByChild("category").equalTo("foods").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val foodList = mutableListOf<FoodBag>()
                val tvTotal:TextView? = view?.findViewById(R.id.tvTotal)
                var total : Long = 0
                for (userSnapshot in snapshot.children) {
                    val image = userSnapshot.child("image").getValue(String::class.java) ?: ""
                    val name = userSnapshot.child("productName").getValue(String::class.java) ?: ""
                    val harga = userSnapshot.child("harga").getValue(Long::class.java) ?: 0
                    val quantity = userSnapshot.child("quantity").getValue(String::class.java) ?: ""
                    val qtyInt = try {
                        quantity.toInt()
                    } catch (e: NumberFormatException) {
                        // Handle the case where "quantity" is not a valid integer
                        0 // or any default value you want to assign
                    }
                    val hargaPerItem :Long = harga * qtyInt
                    total += hargaPerItem
                    tvTotal?.text = formatCurrency(total)
                    val food = FoodBag(image, name, harga, quantity)
                    foodList.add(food)
                }

                // Update data in the adapter
                adapter.dataBagFood = foodList
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