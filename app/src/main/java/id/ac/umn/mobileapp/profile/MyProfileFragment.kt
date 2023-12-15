package id.ac.umn.mobileapp.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import id.ac.umn.mobileapp.R

class MyProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_profile, container, false)

        val btnMyInfo: Button = view.findViewById(R.id.btnMyInfo)
        val btnMyOrders: Button = view.findViewById(R.id.btnMyOrders)
        val btnCreateAccount: Button = view.findViewById(R.id.btnCreateAccount)

        btnMyInfo.setOnClickListener {
            // Pindah ke halaman MyInfoActivity
            val intent = Intent(activity, MyInfoActivity::class.java)
            startActivity(intent)
        }

//        btnMyOrders.setOnClickListener {
//            // Pindah ke halaman MyOrdersActivity
//            val intent = Intent(activity, MyOrdersActivity::class.java)
//            startActivity(intent)
//        }

//        btnCreateAccount.setOnClickListener {
//            // Pindah ke halaman MyWishlistActivity
//            val intent = Intent(activity, MyWishlistActivity::class.java)
//            startActivity(intent)
//        }

        return view
    }
}
