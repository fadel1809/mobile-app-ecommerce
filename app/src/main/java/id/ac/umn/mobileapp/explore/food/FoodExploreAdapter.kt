package id.ac.umn.mobileapp.explore.food

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.ac.umn.mobileapp.R
import java.text.NumberFormat
import java.util.Locale
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FoodExploreAdapter(

    private val dataList: List<Food>
) : RecyclerView.Adapter<FoodExploreAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: ImageView = itemView.findViewById(R.id.imageView)
        val tvNameFood: TextView = itemView.findViewById(R.id.tvNameFood)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHarga)
    }

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference.child("foods")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_explore_food, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        val context = holder.itemView.context

        // Set data to views
        val resourceId = context.resources.getIdentifier(data.imageResourceName, "drawable", context.packageName)
        Glide.with(context).load(resourceId).into(holder.cardView)
        holder.tvNameFood.text = data.tvNameFood

        // Format harga with currency symbol and thousands separator
        val formattedHarga = formatCurrency(data.tvHarga)
        holder.tvHarga.text = formattedHarga

        // Handle item click event
        holder.itemView.setOnClickListener {
            // Save data to Firebase

            // Launch FoodExploreCheckoutActivity with selected item details
            val intent = Intent(context, FoodExploreAddtobagActivity::class.java)
            intent.putExtra("imageResource", data.imageResourceName)
            intent.putExtra("nameFood", data.tvNameFood)
            intent.putExtra("harga", data.tvHarga)
            intent.putExtra("keterangan", data.tvKeterangan)

            context.startActivity(intent)
        }
    }

    // Function to format currency
    private fun formatCurrency(amount: Long): String {
        val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        val symbols = (format as java.text.DecimalFormat).decimalFormatSymbols
        symbols.currencySymbol = "Rp. "
        (format as java.text.DecimalFormat).decimalFormatSymbols = symbols
        return format.format(amount)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}