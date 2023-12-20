package id.ac.umn.mobileapp.explore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.button.MaterialButton
import id.ac.umn.mobileapp.R
import id.ac.umn.mobileapp.explore.option.EducationFragment
import id.ac.umn.mobileapp.explore.option.FashionFragment
import id.ac.umn.mobileapp.explore.option.FoodFragment

class ExploreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_explore, container, false)
        val sharedPreferences = context?.getSharedPreferences("user_data",Context.MODE_PRIVATE)
        val id  = sharedPreferences?.getString("id","")
        showFragment(FashionFragment())

        view.findViewById<MaterialButton>(R.id.btnFashion).setOnClickListener {
            showFragment(FashionFragment())

        }

        view.findViewById<MaterialButton>(R.id.btnFood).setOnClickListener {
            showFragment(FoodFragment())
        }

        view.findViewById<MaterialButton>(R.id.btnEducation).setOnClickListener {
            showFragment(EducationFragment())
        }

        return view
    }

    private fun showFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

}