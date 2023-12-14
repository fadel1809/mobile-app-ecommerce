package id.ac.umn.mobileapp.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import id.ac.umn.mobileapp.R

data class User(
    val email: String = "",
    val title: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val phoneNumber: String = "",
    val address: String = ""
)

class CreateAccountFragment : Fragment() {
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val registerButton: MaterialButton = view.findViewById(R.id.btnRegister)
        registerButton.setOnClickListener {
            val email = view.findViewById<EditText>(R.id.etEmail).text.toString()
            val title = view.findViewById<EditText>(R.id.etTitle).text.toString()
            val firstName = view.findViewById<EditText>(R.id.etFirstName).text.toString()
            val lastName = view.findViewById<EditText>(R.id.etLastName).text.toString()
            val password = view.findViewById<EditText>(R.id.etPassword).text.toString()
            val phoneNumber = view.findViewById<EditText>(R.id.etPhoneNumber).text.toString()
            val address = view.findViewById<EditText>(R.id.etAddress).text.toString()

            saveUserDataToFirebase(email, title, firstName, lastName, password, phoneNumber, address)
        }
    }

    private fun saveUserDataToFirebase(email: String, title: String, firstName: String, lastName: String, password: String, phoneNumber: String, address: String) {
        val usersReference = databaseReference.child("users")
        val user = User(email, title, firstName, lastName, password, phoneNumber, address)
        val userId = usersReference.push().key
        userId?.let {
            usersReference.child(it).setValue(user)
            showToast("Registration successful")
            navigateToProfileFragment()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToProfileFragment() {
        val profileFragment = ProfileFragment() // Ganti dengan nama kelas fragment profil Anda
        val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, profileFragment) // Ganti dengan ID container fragment Anda
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
