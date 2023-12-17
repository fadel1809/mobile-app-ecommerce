package id.ac.umn.mobileapp.explore.fashion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import id.ac.umn.mobileapp.R

class FashionExploreAdapter(private val dataList: List<YourDataModel>) :
    RecyclerView.Adapter<FashionExploreAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: ImageView = itemView.findViewById(R.id.imageView)
        val tvNameFashion: TextView = itemView.findViewById(R.id.tvNameFashion)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHarga)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_explore_fashion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]

        // Set data ke dalam views
        holder.cardView.setImageResource(data.imageResource)
        holder.tvNameFashion.text = data.tvNameFashion
        holder.tvHarga.text = data.tvHarga
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
