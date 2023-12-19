// FoodExploreAdapter.kt
package id.ac.umn.mobileapp.explore.food

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import id.ac.umn.mobileapp.R
import java.text.NumberFormat
import java.util.Locale

class FoodExploreAdapter(
    private val context: Context,
    private val dataList: List<YourDataModel>
) : RecyclerView.Adapter<FoodExploreAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: ImageView = itemView.findViewById(R.id.imageView)
        val tvNameFood: TextView = itemView.findViewById(R.id.tvNameFood)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHarga)
    }

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference.child("food")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_explore_food, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]

        // Set data to views
        holder.cardView.setImageResource(data.imageResource)
        holder.tvNameFood.text = data.tvNameFood

        // Format harga with currency symbol and thousands separator
        val formattedHarga = formatCurrency(data.tvHarga)
        holder.tvHarga.text = formattedHarga

        // Handle item click event
        holder.itemView.setOnClickListener {
            // Save data to Firebase
            saveDataToFirebase(data)

            // Launch FoodExploreCheckoutActivity with selected item details
            val intent = Intent(context, FoodExploreAddtobagActivity::class.java).apply {
                putExtra("imageResource", data.imageResource)
                putExtra("nameFood", data.tvNameFood)
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
