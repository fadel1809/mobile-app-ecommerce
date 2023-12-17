package id.ac.umn.mobileapp.profile.address

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.ac.umn.mobileapp.R
import id.ac.umn.mobileapp.profile.MyInfoActivity

class EditAddressActivity : AppCompatActivity() {
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
    override fun onCreate(savedInstanceState: Bundle?) {
        databaseReference = FirebaseDatabase.getInstance().getReference("users")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_address)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        if(intent.hasExtra("id")){
            val id = intent.getStringExtra("id")
            if(id!= null){
                setupFirebase(id)
            }
        }

        if (intent.hasExtra("email")) {
            val email = intent.getStringExtra("email")
            val id = intent.getStringExtra("id")
            if (email != null && id != null) {
                val btnSaveModification: Button = findViewById(R.id.btnSaveModificationAddress)
                btnSaveModification.setOnClickListener {
                    Log.d("clicked","clicked")
                    val newAddress = findViewById<EditText?>(R.id.etAddress).text.toString()

                    if (newAddress.isNotEmpty()) {
                        // Update the data in the database
                        updateProfile(id, email,newAddress)
                    } else {
                        Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Add navigation icon
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Add onClickListener for the navigation icon
        toolbar.setNavigationOnClickListener {
            // Go back to the previous fragment or perform the appropriate operation
            finish()
        }

    }
    private fun setupFirebase(id: String) {
        databaseReference.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Assuming there is only one child under the specified ID
                    val userData = snapshot.getValue(User::class.java)

                    // Check if userData is not null before accessing its properties
                    if (userData != null) {
                        val etAddress = findViewById<EditText?>(R.id.etAddress)

                        etAddress.setText(userData.address)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("error", error.toString())
            }
        })
    }

    private fun updateProfile(id: String, userEmail: String, newAddress:String) {
        // Reference to the user's data in the database
        val userReference = databaseReference.child(id)

        // Create a map with the new values
        val updates = mapOf(
            "address" to newAddress
        )

        try {
            userReference.updateChildren(updates)
                .addOnSuccessListener {
                    Toast.makeText(this, "Address updated successfully", Toast.LENGTH_SHORT).show()
                    // Pass the updated information back to MyInfoActivity
                    navigateToMyInfoActivity(userEmail, id)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to update address", Toast.LENGTH_SHORT).show()
                }
        } catch (e: Exception) {
            Log.e("err", e.toString())
        }
    }
    private fun navigateToMyInfoActivity(email: String, id: String) {
        val intent = Intent(this, MyInfoActivity::class.java)
        intent.putExtra("email", email)
        intent.putExtra("id", id)
        startActivity(intent)
        finish()
    }
}