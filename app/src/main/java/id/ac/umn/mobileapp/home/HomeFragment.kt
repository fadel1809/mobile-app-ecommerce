package id.ac.umn.mobileapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import id.ac.umn.mobileapp.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val imageSlider: ImageSlider = view.findViewById(R.id.image_slider)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel("android.resource://${requireActivity().packageName}/${R.drawable.carousel1}"))
        imageList.add(SlideModel("android.resource://${requireActivity().packageName}/${R.drawable.carousel2}"))
        imageList.add(SlideModel("android.resource://${requireActivity().packageName}/${R.drawable.carousel3}"))

        imageSlider.setImageList(imageList, ScaleTypes.CENTER_INSIDE)

        // Set up RecyclerView with drawable resource IDs
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_images)
        val imageResourceIds = listOf(
            R.drawable.home_fashion,
            R.drawable.home_food,
            R.drawable.home_education,

        )
        val imageAdapter = ImageAdapter(imageResourceIds)
        recyclerView.adapter = imageAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        return view
    }
}

