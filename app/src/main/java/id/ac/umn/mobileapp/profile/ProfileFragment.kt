package id.ac.umn.mobileapp.profile


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.button.MaterialButton
import id.ac.umn.mobileapp.R



class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sharedPreferences = context?.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val email = sharedPreferences?.getString("email", "User")
        val id = sharedPreferences?.getString("id","")
        if( email != null && id != null){
            val myProfileFragment = MyProfileFragment() // Ganti dengan nama kelas fragment profil Anda
            val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction?.replace(R.id.frame_container, myProfileFragment) // Ganti dengan ID container fragment Anda
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val btnLogin = view.findViewById<MaterialButton>(R.id.btnLogin)
        val btnCreateAccount = view.findViewById<MaterialButton>(R.id.btnCreateAccount)

        btnLogin.setOnClickListener {
            // Ketika btnLogin diklik, buat instance dari LoginFragment
            val loginFragment = LoginFragment()

            // Ganti fragment menggunakan FragmentTransaction
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frame_container, loginFragment)
                addToBackStack(null) // Menambahkan fragment ke stack kembali
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                commit()
            }
        }

        btnCreateAccount.setOnClickListener {
            // Ketika btnCreateAccount diklik, buat instance dari CreateAccountFragment
            val createAccountFragment = CreateAccountFragment()

            // Ganti fragment menggunakan FragmentTransaction
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frame_container, createAccountFragment)
                addToBackStack(null) // Menambahkan fragment ke stack kembali
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                commit()
            }
        }

        return view
    }

    // ... (kode yang lain)
}
