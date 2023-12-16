package id.ac.umn.mobileapp.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
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
        if (email != null) {
            setupFirebaseListener(email)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        databaseReference = FirebaseDatabase.getInstance().getReference("users")
        val view = inflater.inflate(R.layout.fragment_my_profile, container, false)

        val btnMyInfo: Button = view.findViewById(R.id.btnMyInfo)
        // val btnMyOrders: Button = view.findViewById(R.id.btnMyOrders)
        // val btnCreateAccount: Button = view.findViewById(R.id.btnCreateAccount)
        // val tvName: TextView = view.findViewById(R.id.tvNama)

        btnMyInfo.setOnClickListener {
//             Pindah ke halaman MyInfoActivity
             val intent = Intent(activity, MyInfoActivity::class.java)
             startActivity(intent)
        }

        return view
    }

    private fun setupFirebaseListener(userEmail: String) {
        // Query to find the user with the matching email
        val query = databaseReference.orderByChild("email").equalTo(userEmail)

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
                } else {
                    // Handle the case where no user with the specified email was found
                    // You can show an error message or take appropriate action
                    Toast.makeText(requireContext(), "something wrong", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireContext(), "Something wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
