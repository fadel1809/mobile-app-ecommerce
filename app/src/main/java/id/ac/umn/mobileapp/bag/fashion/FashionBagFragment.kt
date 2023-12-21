// FashionBagFragment.kt
package id.ac.umn.mobileapp.bag.fashion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
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
import id.ac.umn.mobileapp.profile.LoginFragment
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
        val sharedPreferences = context?.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val id = sharedPreferences?.getString("id", null)
        if (id == null) {

             Toast.makeText(requireContext(), "Login Terlebih dahulu", Toast.LENGTH_SHORT).show()
//            val intent = Intent(requireContext(),MyProfileFragment::class.java)
//            startActivity(intent)
        }else{
            fetchUserData()
        }




            val btnCO: MaterialButton? = view?.findViewById(R.id.btnCheckout)
            btnCO?.setOnClickListener {
                val intent = Intent(requireContext(), CheckoutActivity::class.java)

                // Pass tvTotal to CheckoutActivity
                val tvTotal: TextView? = view?.findViewById(R.id.tvTotal)
                val totalAmount = tvTotal?.text?.toString() ?: "0"
                intent.putExtra("TOTAL_AMOUNT", totalAmount)

                // Pass data from the snapshot to CheckoutActivity
                val fashionList =
                    adapter.dataBagFashion // Assuming fashionList is the list containing snapshot data
                val dataBundle = Bundle()

                for (fashion in fashionList) {
                    val itemBundle = Bundle()
                    itemBundle.putString("image", fashion.imageResourceName)
                    itemBundle.putString("productName", fashion.tvNameFashion)
                    itemBundle.putLong("harga", fashion.tvHarga)
                    itemBundle.putString("selectedSize", fashion.selectedSize)
                    itemBundle.putString("quantity", fashion.tvQuantity)

                    // Use a unique key for each item, e.g., "ITEM_1", "ITEM_2", etc.
                    dataBundle.putBundle("ITEM_${fashionList.indexOf(fashion) + 1}", itemBundle)
                }

                intent.putExtra("DATA_BUNDLE", dataBundle)
                startActivity(intent)
            }


            return view


}

    private fun fetchUserData() {
        databaseReference.orderByChild("category").equalTo("fashion").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tvTotal:TextView? =view?.findViewById(R.id.tvTotal)
                val fashionList = mutableListOf<FashionBag>()
                var total : Long = 0
                for (userSnapshot in snapshot.children) {
                    val image = userSnapshot.child("image").getValue(String::class.java) ?: ""
                    val name = userSnapshot.child("productName").getValue(String::class.java) ?: ""
                    val harga = userSnapshot.child("harga").getValue(Long::class.java) ?: 0
                    val size = userSnapshot.child("selectedSize").getValue(String::class.java) ?: ""
                    val quantity = userSnapshot.child("quantity").getValue(String::class.java) ?: ""
                    val qtyInt = try{
                        quantity.toInt()
                    }catch (e: NumberFormatException){
                        0
                    }

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
