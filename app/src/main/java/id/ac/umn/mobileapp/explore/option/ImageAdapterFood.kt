package id.ac.umn.mobileapp.explore.option

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.ac.umn.mobileapp.R
import id.ac.umn.mobileapp.home.ImageAdapter

class ImageAdapterFood(private val imageResourceIds: List<Int>) :
    RecyclerView.Adapter<ImageAdapterFood.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_food, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Load images from drawable resources
        Glide.with(holder.itemView.context)
            .load(imageResourceIds[position])
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return imageResourceIds.size
    }
}
