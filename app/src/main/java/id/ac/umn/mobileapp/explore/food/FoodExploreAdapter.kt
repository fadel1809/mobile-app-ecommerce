package id.ac.umn.mobileapp.explore.food

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import id.ac.umn.mobileapp.R

class FoodExploreAdapter(private val dataList: List<YourDataModel>) :
    RecyclerView.Adapter<FoodExploreAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: ImageView = itemView.findViewById(R.id.imageView)
        val tvNameFood: TextView = itemView.findViewById(R.id.tvNameFood)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHarga)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_explore_food, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]

        // Set data ke dalam views
        holder.cardView.setImageResource(data.imageResource)
        holder.tvNameFood.text = data.tvNameFood
        holder.tvHarga.text = data.tvHarga
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
