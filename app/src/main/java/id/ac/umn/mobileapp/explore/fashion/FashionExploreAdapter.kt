//package id.ac.umn.mobileapp.explore.fashion
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import id.ac.umn.mobileapp.R
//
//class FashionExploreAdapter(private val dataList: MutableList<FashionExploreActivity.fashion>) :
//    RecyclerView.Adapter<FashionExploreAdapter.ViewHolder>() {
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val cardView: ImageView = itemView.findViewById(R.id.imageView)
//        val tvNameFashion: TextView = itemView.findViewById(R.id.tvNameFashion)
//        val tvHarga: TextView = itemView.findViewById(R.id.tvHarga)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.recyclerview_explore_fashion, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val data = dataList[position]
//
//        // Set data ke dalam views
//        holder.cardView.setImageResource(data.imageResource)
//        holder.tvNameFashion.text = data.tvNameFashion
//        holder.tvHarga.text = data.tvHarga
//    }
//
//    override fun getItemCount(): Int {
//        return dataList.size
//    }
//}
package id.ac.umn.mobileapp.explore.fashion

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.umn.mobileapp.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import id.ac.umn.mobileapp.explore.food.FoodExploreAddtobagActivity
import java.text.NumberFormat
import java.util.Locale

class FashionExploreAdapter(private val dataList: List<YourDataModel>,private val context: Context) :
    RecyclerView.Adapter<FashionExploreAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: ImageView = itemView.findViewById(R.id.imageView)
        val tvNameFashion: TextView = itemView.findViewById(R.id.tvNameFashion)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHarga)
    }

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference.child("fashion")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_explore_fashion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val data = dataList[position]

        // Set data to views
        holder.cardView.setImageResource(data.imageResource)
        holder.tvNameFashion.text = data.tvNameFashion

        // Format harga with currency symbol and thousands separator
        val formattedHarga = formatCurrency(data.tvHarga)
        holder.tvHarga.text = formattedHarga

        // Handle item click event
        holder.itemView.setOnClickListener {
            // Save data to Firebase
            saveDataToFirebase(data)

            // Launch FoodExploreCheckoutActivity with selected item details
            val intent = Intent(context, FashionExploreCatalogActivity::class.java).apply {
                putExtra("imageResource", data.imageResource)
                putExtra("nameFood", data.tvNameFashion)
                putExtra("harga", data.tvHarga)
                putExtra("keterangan", data.tvKeterangan)
            }
            context.startActivity(intent)
        }
    }

    // Function to format currency
    private fun formatCurrency(amount: Int): String {
        val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        return format.format(amount.toLong())
    }

    // Function to save data to Firebase
    private fun saveDataToFirebase(data: YourDataModel) {
        // Generate a unique key for each entry
        val key = database.push().key

        // Check if the key is not null
        key?.let {
            // Save data to Firebase under "food" reference with the generated key
            database.child(it).setValue(data)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}




