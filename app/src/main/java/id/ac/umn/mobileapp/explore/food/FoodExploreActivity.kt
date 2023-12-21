package id.ac.umn.mobileapp.explore.food

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

class FoodExploreActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_explore_catalog)

        recyclerView = findViewById(R.id.recyclerView)
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        databaseReference = FirebaseDatabase.getInstance().getReference("foods")

        // Add navigation icon
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Add onClickListener for the navigation icon
        toolbar.setNavigationOnClickListener {
            // Navigate back to the previous fragment or perform appropriate operation
            onBackPressed()
        }

        // Example data
        fetchFoodData()
    }

    private fun fetchFoodData() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val foodList = mutableListOf<Food>()
                for (foodSnapshot in snapshot.children) {
                    val imageResource = foodSnapshot.child("image").getValue(String::class.java) ?: ""
                    val nameFood = foodSnapshot.child("name").getValue(String::class.java) ?: ""
                    val harga = foodSnapshot.child("harga").getValue(Long::class.java) ?: 0
                    val keterangan = foodSnapshot.child("keterangan").getValue(String::class.java) ?: ""

                    val food = Food(imageResource, nameFood, harga, keterangan)
                    foodList.add(food)
                }

                val adapter = FoodExploreAdapter(foodList)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
            }
        })
    }
}