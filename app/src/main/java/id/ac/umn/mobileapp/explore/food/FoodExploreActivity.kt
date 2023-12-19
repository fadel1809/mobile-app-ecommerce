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
            YourDataModel(R.drawable.food_explore_1, "Burger", 100000, "Hamburger atau burger adalah sejenis roti berbentuk bundar yang diiris dua, dan di tengahnya diisi dengan patty yang biasanya diambil dari daging, kemudian sayur-sayuran berupa selada, tomat dan bawang bombai. Sebagai sausnya, hamburger diberi berbagai jenis saus seperti mayones, saus tomat dan sambal, serta moster. Beberapa varian hamburger juga dilengkapi dengan keju dan asinan. Hamburger merupakan makanan siap saji yang populer di Amerika Serikat."),
            YourDataModel(R.drawable.food_explore_2, "Chicken", 400000, "Chicken is the most common type of poultry in the world. Owing to the relative ease and low cost of raising chickens—in comparison to mammals such as cattle or hogs—chicken meat (commonly called just \"chicken\") and chicken eggs have become prevalent in numerous cuisines.\n" +
                    "\n" +
                    "Chicken can be prepared in a vast range of ways, including baking, grilling, barbecuing, frying, and boiling. Since the latter half of the 20th century, prepared chicken has become a staple of fast food. Chicken is sometimes cited as being more healthful than red meat, with lower concentrations of cholesterol and saturated fat"),
            YourDataModel(R.drawable.food_explore_3, "Warehouse", 200000, "Most carideans are omnivorous, but some are specialised for particular modes of feeding. Some are filter feeders, using their setose (bristly) legs as a sieve; some scrape algae from rocks. The snapping shrimp of the genus Alpheus snap their claws to create a shock wave that stuns prey. Many cleaner shrimp, which groom reef fish and feed on their parasites and necrotic tissue, are carideans. In turn, carideans are eaten by various animals, particularly fish and seabirds, and frequently host bopyrid parasites."),
            YourDataModel(R.drawable.food_explore_4, "Warehouse With Chicken", 100000, "Most carideans are omnivorous, but some are specialised for particular modes of feeding. Some are filter feeders, using their setose (bristly) legs as a sieve; some scrape algae from rocks. The snapping shrimp of the genus Alpheus snap their claws to create a shock wave that stuns prey. Many cleaner shrimp, which groom reef fish and feed on their parasites and necrotic tissue, are carideans. In turn, carideans are eaten by various animals, particularly fish and seabirds, and frequently host bopyrid parasites."),
            YourDataModel(R.drawable.food_explore_5, "Warehouse2", 50000, "Most carideans are omnivorous, but some are specialised for particular modes of feeding. Some are filter feeders, using their setose (bristly) legs as a sieve; some scrape algae from rocks. The snapping shrimp of the genus Alpheus snap their claws to create a shock wave that stuns prey. Many cleaner shrimp, which groom reef fish and feed on their parasites and necrotic tissue, are carideans. In turn, carideans are eaten by various animals, particularly fish and seabirds, and frequently host bopyrid parasites.")
        )

        // Pass data list to the adapter
        val adapter = FoodExploreAdapter(this, dataList)
        recyclerView.adapter = adapter
    }
}
