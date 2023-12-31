package id.ac.umn.mobileapp.bag

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
import id.ac.umn.mobileapp.bag.fashion.FashionBagFragment
import id.ac.umn.mobileapp.bag.food.FoodBagFragment
import id.ac.umn.mobileapp.explore.option.EducationFragment
import id.ac.umn.mobileapp.explore.option.FashionFragment
import id.ac.umn.mobileapp.explore.option.FoodFragment

class BagFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bag, container, false)

        showFragment(FashionBagFragment())

        view.findViewById<MaterialButton>(R.id.btnFashion).setOnClickListener {
            showFragment(FashionBagFragment())

        }

        view.findViewById<MaterialButton>(R.id.btnFood).setOnClickListener {
            showFragment(FoodBagFragment())
        }

        return view
    }

    private fun showFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

}