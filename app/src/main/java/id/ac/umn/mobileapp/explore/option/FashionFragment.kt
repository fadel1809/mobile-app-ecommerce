package id.ac.umn.mobileapp.explore.option

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.umn.mobileapp.R

class FashionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fashion, container, false)

        // Set up RecyclerView with drawable resource IDs
        val recyclerView: RecyclerView = view.findViewById(R.id.rvFashion)
        val imageResourceIds = listOf(
            R.drawable.fashion_shoes,
            R.drawable.fashion_trousers,
            R.drawable.fashion_tshirt,
            )

        val imageAdapter = ImageAdapterFashion(imageResourceIds)
        recyclerView.adapter = imageAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        return view
    }
}

