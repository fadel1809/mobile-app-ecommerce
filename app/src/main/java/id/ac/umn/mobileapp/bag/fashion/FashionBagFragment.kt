// FashionBagFragment.kt
package id.ac.umn.mobileapp.bag.fashion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.ac.umn.mobileapp.R

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

        return view
    }

    private fun fetchUserData() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fashionList = mutableListOf<FashionBag>()
                for (userSnapshot in snapshot.children) {
                    val image = userSnapshot.child("image").getValue(String::class.java) ?: ""
                    val name = userSnapshot.child("productName").getValue(String::class.java) ?: ""
                    val harga = userSnapshot.child("harga").getValue(Long::class.java) ?: 0
                    val keterangan = userSnapshot.child("keterangan").getValue(String::class.java) ?: ""

                    val fashion = FashionBag(image, name, harga, keterangan)
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
}
