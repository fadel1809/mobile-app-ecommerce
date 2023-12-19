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
import id.ac.umn.mobileapp.explore.education.EducationExploreActivity
import id.ac.umn.mobileapp.explore.fashion.FashionExploreActivity

class EducationFragment : Fragment() , ItemClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_education, container, false)

        // Set up RecyclerView with drawable resource IDs
        val recyclerView: RecyclerView = view.findViewById(R.id.rvEducation)
        val imageResourceIds = listOf(
            R.drawable.home_education,
            R.drawable.education_it,
            R.drawable.education_medical,
            )

        val imageAdapter = ImageAdapterFashion(imageResourceIds)
        imageAdapter.itemClickListener = this
        recyclerView.adapter = imageAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        return view
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(requireContext(), EducationExploreActivity::class.java)
        startActivity(intent)
    }
}

