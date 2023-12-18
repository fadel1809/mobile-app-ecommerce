package id.ac.umn.mobileapp.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.database.*

import id.ac.umn.mobileapp.R

class MyProfileFragment : Fragment() {
    data class User(
        val email: String = "",
        val title: String = "",
        val firstName: String = "",
        val lastName: String = "",
        val password: String = "",
        val phoneNumber: String = "",
        val address: String = ""
    )

    private lateinit var databaseReference: DatabaseReference

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val sharedPreferences = context?.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val email = sharedPreferences?.getString("email", "User")
        val id = sharedPreferences?.getString("id","")
        if (email != null && id  != null) {
            setupFirebaseListener(email,id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        databaseReference = FirebaseDatabase.getInstance().getReference("users")
        val view = inflater.inflate(R.layout.fragment_my_profile, container, false)
        val sharedPreferences = context?.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val email = sharedPreferences?.getString("email", "User")
        val id = sharedPreferences?.getString("id","")

        if (email != null && id !=null) {
            setupFirebaseListener(email, id)
        } else {
            val sharedPreferences= context?.getSharedPreferences("user_data", Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.remove("email")
            editor?.remove("id")
            editor?.apply()
            val myProfileFragment = LoginFragment()// Ganti dengan nama kelas fragment profil Anda
            val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction?.replace(R.id.frame_container, myProfileFragment) // Ganti dengan ID container fragment Anda
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
        val btnMyInfo: Button = view.findViewById(R.id.btnMyInfo)
        val btnLogout: Button = view.findViewById(R.id.btnLogout)
        // val btnMyOrders: Button = view.findViewById(R.id.btnMyOrders)
        // val btnCreateAccount: Button = view.findViewById(R.id.btnCreateAccount)
        // val tvName: TextView = view.findViewById(R.id.tvNama)

        btnMyInfo.setOnClickListener {
//          Pindah ke halaman MyInfoActivity
            val intent = Intent(activity, MyInfoActivity::class.java)
            intent.putExtra("email",email)
            intent.putExtra("id",id)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            val sharedPreferences= context?.getSharedPreferences("user_data", Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.remove("email")
            editor?.remove("id")
            editor?.apply()
            val myProfileFragment = LoginFragment()// Ganti dengan nama kelas fragment profil Anda
            val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction?.replace(R.id.frame_container, myProfileFragment) // Ganti dengan ID container fragment Anda
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

        return view
    }

    private fun setupFirebaseListener(userEmail: String,id:String) {
        // Query to find the user with the matching email
        val query = databaseReference.orderByChild("email").equalTo(userEmail)
        var btnLogout = view?.findViewById<Button?>(R.id.btnLogout)
        // Listen for changes in the database
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called when data changes in the database

                // Assuming you have TextViews with the IDs "firstNameTextView" and "lastNameTextView" in your layout
                val tvName: TextView? = view?.findViewById(R.id.tvNama)

                // Check if the query returned any results
                if (dataSnapshot.exists()) {
                    for (userSnapshot in dataSnapshot.children) {
                        // Retrieve data from Firebase
                        val firstName = userSnapshot.child("firstName").getValue(String::class.java)
                        val lastName = userSnapshot.child("lastName").getValue(String::class.java)

                        // Update the TextViews with the retrieved data
                        tvName?.text = "$firstName $lastName"
                    }
                    btnLogout?.text = "LOGOUT"
                } else {
                    // Handle the case where no user with the specified email was found
                    // You can show an error message or take appropriate action
                    btnLogout?.text = "LOGIN"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireContext(), "Something wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
