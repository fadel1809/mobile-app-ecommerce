package id.ac.umn.mobileapp.explore.fashion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.ac.umn.mobileapp.R

class FashionExploreActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fashion_explore)

        recyclerView = findViewById(R.id.recyclerView)
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        databaseReference = FirebaseDatabase.getInstance().getReference("fashion")

        // Tambahkan ikon navigasi
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Tambahkan onClickListener untuk ikon navigasi
        toolbar.setNavigationOnClickListener {
            // Kembali ke fragment sebelumnya atau lakukan operasi yang sesuai
            onBackPressed()
        }

        // Contoh data
        fetchUserData()
    }

    private fun fetchUserData() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fashionList = mutableListOf<Fashion>()
                for (userSnapshot in snapshot.children) {
                    val image = userSnapshot.child("image").getValue(String::class.java) ?: ""
                    val name = userSnapshot.child("name").getValue(String::class.java) ?: ""
                    val harga = userSnapshot.child("harga").getValue(Long::class.java) ?: 0
                    val keterangan = userSnapshot.child("keterangan").getValue(String::class.java) ?: ""

                    val fashion = Fashion(image, name, harga, keterangan)
                    fashionList.add(fashion)
                }

                val adapter = FashionExploreAdapter(fashionList)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
            }
        })
    }
}
