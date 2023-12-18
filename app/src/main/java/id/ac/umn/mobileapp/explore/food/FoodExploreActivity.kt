// FoodExploreActivity.kt
package id.ac.umn.mobileapp.explore.food

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.umn.mobileapp.R

class FoodExploreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_explore)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Tambahkan ikon navigasi
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Tambahkan onClickListener untuk ikon navigasi
        toolbar.setNavigationOnClickListener {
            // Kembali ke fragment sebelumnya atau lakukan operasi yang sesuai
            onBackPressed()
        }

        // Contoh data
        val dataList = listOf(
            YourDataModel(R.drawable.food_explore_1, "Burger", "Rp.100.000", "Deskripsi Burger"),
            YourDataModel(R.drawable.food_explore_2, "Chicken", "Rp.400.000", "Deskripsi Chicken"),
            YourDataModel(R.drawable.food_explore_3, "Warehouse", "Rp.200.000", "Deskripsi Warehouse"),
            YourDataModel(R.drawable.food_explore_4, "Warehouse With Chicken", "Rp.100.000", "Deskripsi Warehouse With Chicken"),
            YourDataModel(R.drawable.food_explore_5, "Warehouse2", "Rp.50.000", "Deskripsi Warehouse2")
        )

        // Pass data list to the adapter
        val adapter = FoodExploreAdapter(this, dataList)
        recyclerView.adapter = adapter
    }
}
