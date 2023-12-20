// FashionBagFragment.kt
package id.ac.umn.mobileapp.bag.fashion

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.ac.umn.mobileapp.R
import id.ac.umn.mobileapp.checkout.CheckoutActivity
import id.ac.umn.mobileapp.profile.MyProfileFragment
import java.text.NumberFormat
import java.util.Locale

class FashionBagFragment : Fragment() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FashionBagAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fashion_bag, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        val layoutManager = GridLayoutManager(requireContext(), 1)
        recyclerView.layoutManager = layoutManager

        // Initialize adapter outside the fetchUserData method
        adapter = FashionBagAdapter(emptyList())
        recyclerView.adapter = adapter

        databaseReference = FirebaseDatabase.getInstance().getReference("bag")

        // Fetch data and update adapter
        fetchUserData()
        val btnCO:MaterialButton? = view?.findViewById(R.id.btnCheckout)
        btnCO?.setOnClickListener{

            val intent = Intent(requireContext(),CheckoutActivity::class.java)
            startActivity(intent)

        }

        return view
    }

    private fun fetchUserData() {
        databaseReference.orderByChild("category").equalTo("fashion").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tvTotal:TextView? =view?.findViewById(R.id.tvTotal)
                val fashionList = mutableListOf<FashionBag>()
                for (userSnapshot in snapshot.children) {
                    val image = userSnapshot.child("image").getValue(String::class.java) ?: ""
                    val name = userSnapshot.child("productName").getValue(String::class.java) ?: ""
                    val harga = userSnapshot.child("harga").getValue(Long::class.java) ?: 0
                    val size = userSnapshot.child("selectedSize").getValue(String::class.java) ?: ""
                    val quantity = userSnapshot.child("quantity").getValue(String::class.java) ?: ""
                    val qtyInt = quantity.toInt()
                    var total : Long = 0
                    val hargaPerItem :Long = harga * qtyInt
                    total  = total + hargaPerItem
                    tvTotal?.text = formatCurrency(total)
                    val fashion = FashionBag(image, name, harga, size,quantity)
                    fashionList.add(fashion)
                }

                // Update data in the adapter
                adapter.dataBagFashion = fashionList
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
