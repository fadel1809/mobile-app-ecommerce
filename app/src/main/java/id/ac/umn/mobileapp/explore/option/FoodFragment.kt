package id.ac.umn.mobileapp.explore.option

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.umn.mobileapp.R
import id.ac.umn.mobileapp.explore.food.FoodExploreActivity

class FoodFragment : Fragment(), ItemClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_food, container, false)

        // Set up RecyclerView with drawable resource IDs
        val recyclerView: RecyclerView = view.findViewById(R.id.rvFood)
        val imageResourceIds = listOf(
            R.drawable.food_chicken,
            R.drawable.food_bowl,
            R.drawable.food_seafood
        )
        val imageAdapter = ImageAdapterFood(imageResourceIds)
        imageAdapter.itemClickListener = this
        recyclerView.adapter = imageAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        return view
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(requireContext(), FoodExploreActivity::class.java)
        startActivity(intent)
    }
}
