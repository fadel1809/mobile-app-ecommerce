// FashionBagAdapter.kt
package id.ac.umn.mobileapp.bag.fashion

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import id.ac.umn.mobileapp.R
import java.text.NumberFormat
import java.util.Locale

class FashionBagAdapter(var dataBagFashion: List<FashionBag>) :
    RecyclerView.Adapter<FashionBagAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: ImageView = itemView.findViewById(R.id.imageView)
        val tvNameFashion: TextView = itemView.findViewById(R.id.tvNameFashion)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHarga)
        val tvSize:TextView = itemView.findViewById(R.id.tvSize)
        val tvQuantity: TextView = itemView.findViewById(R.id.tvQuantity)
    }

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference.child("bag")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_fashion_bag, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataBagFashion.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataBagFashion[position]
        val context = holder.itemView.context

        // Set data to views
        val resourceId = context.resources.getIdentifier(data.imageResourceName, "drawable", context.packageName)
        Glide.with(context).load(resourceId).into(holder.cardView)
        holder.tvNameFashion.text = data.tvNameFashion

        // Format harga with currency symbol and thousands separator
        val formattedHarga = formatCurrency(data.tvHarga)
        holder.tvHarga.text = formattedHarga
        holder.tvSize.setText(data.selectedSize)
        holder.tvQuantity.text = data.tvQuantity
    }

    private fun formatCurrency(amount: Long): String {
        val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        val symbols = (format as java.text.DecimalFormat).decimalFormatSymbols
        symbols.currencySymbol = "Rp. "
        (format as java.text.DecimalFormat).decimalFormatSymbols = symbols
        return format.format(amount)
    }
}

