package id.ac.umn.mobileapp.explore.fashion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.umn.mobileapp.R

class FashionExploreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fashion_explore)


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
            YourDataModel(R.drawable.fashion_explore_jacket, "Jacket", "Rp.100.000"),
            YourDataModel(R.drawable.fashion_explore_pants, "Pants", "Rp.400.000"),
            YourDataModel(R.drawable.fashion_explore_sandals, "Sandals", "Rp.200.000"),
            YourDataModel(R.drawable.fashion_explore_shirt, "Shirt", "Rp.100.000"),
            YourDataModel(R.drawable.fashion_explore_shortpants, "Shortpants", "Rp.50.000"),
            YourDataModel(R.drawable.fashion_explore_shoes, "Shoes", "Rp.50.000"),
            YourDataModel(R.drawable.fashion_explore_sweater, "Sweater", "Rp.50.000")
        )

        val adapter = FashionExploreAdapter(dataList)
        recyclerView.adapter = adapter
    }
}
