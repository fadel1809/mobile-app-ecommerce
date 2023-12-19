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
            YourDataModel(R.drawable.fashion_explore_jacket, "Jacket", 100000, "This is jacket"),
            YourDataModel(R.drawable.fashion_explore_pants, "Pants", 400000, "Thsadsa"),
            YourDataModel(R.drawable.fashion_explore_sandals, "Sandals", 200000, "sdadsa"),
            YourDataModel(R.drawable.fashion_explore_shirt, "Shirt", 100000, "sdsadsa"),
            YourDataModel(R.drawable.fashion_explore_shortpants, "Shortpants", 50000, "sadsada"),
            YourDataModel(R.drawable.fashion_explore_shoes, "Shoes", 50000, "sadasd"),
            YourDataModel(R.drawable.fashion_explore_sweater, "Sweater", 50000, "sasadsadsa")
        )

        val adapter = FashionExploreAdapter(dataList,this)
        recyclerView.adapter = adapter
    }
}

