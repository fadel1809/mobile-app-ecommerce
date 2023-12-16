package id.ac.umn.mobileapp.profile

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.ac.umn.mobileapp.R



class LoginFragment : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Inisialisasi Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().reference.child("users")

        // UI Components
        val emailEditText: EditText = view.findViewById(R.id.etEmail)
        val passwordEditText: EditText = view.findViewById(R.id.etPassword)
        val loginButton: Button = view.findViewById(R.id.btnLogin)

        // Login Button Click Listener
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Panggil fungsi login
            loginUser(email, password)
        }

        return view
    }

    private fun loginUser(email: String, password: String) {

        databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val sharedPrefs = context?.getSharedPreferences("user_data",Context.MODE_PRIVATE)
                    val editor = sharedPrefs?.edit()
                    editor?.putString("email",email)
                    editor?.apply()
                    for (userSnapshot in snapshot.children){
                        val userData =  userSnapshot.getValue(User::class.java)
                        if(userData != null && userData.password == password){

                            Toast.makeText(requireContext(),"Login successful",Toast.LENGTH_SHORT).show()
                            navigateToProfileFragment()
                        }else{
                            Toast.makeText(requireContext(),"Password Salah",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
                Toast.makeText(requireContext(),"Login failed",Toast.LENGTH_SHORT).show()
            }
            private fun navigateToProfileFragment() {
                val myProfileFragment = MyProfileFragment() // Ganti dengan nama kelas fragment profil Anda
                val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction?.replace(R.id.frame_container, myProfileFragment) // Ganti dengan ID container fragment Anda
                transaction?.addToBackStack(null)
                transaction?.commit()
            }
        })
    }


}