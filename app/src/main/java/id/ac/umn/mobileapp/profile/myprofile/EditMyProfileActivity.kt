package id.ac.umn.mobileapp.profile.myprofile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.ac.umn.mobileapp.R
import id.ac.umn.mobileapp.profile.MyInfoActivity

class EditMyProfileActivity : AppCompatActivity() {
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
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_my_profile)

        databaseReference = FirebaseDatabase.getInstance().getReference("users")

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
                val btnSaveModification: MaterialButton = findViewById(R.id.btnSaveModification)
                btnSaveModification.setOnClickListener {

                    val newTitle = findViewById<EditText?>(R.id.tieTitle).text.toString()
                    val newFirstName = findViewById<EditText?>(R.id.tieFirstName).text.toString()
                    val newLastName = findViewById<EditText?>(R.id.tieLastName).text.toString()

                    if (newTitle.isNotEmpty() && newFirstName.isNotEmpty() && newLastName.isNotEmpty()) {
                        // Update the data in the database
                        updateProfile(id, email, newTitle, newFirstName, newLastName)
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
                        val tieTitle = findViewById<EditText?>(R.id.tieTitle)
                        val tieFirstName = findViewById<EditText?>(R.id.tieFirstName)
                        val tieLastName = findViewById<EditText?>(R.id.tieLastName)

                        tieTitle.setText(userData.title)
                        tieFirstName.setText(userData.firstName)
                        tieLastName.setText(userData.lastName)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("error", error.toString())
            }
        })
    }

    private fun updateProfile(id: String, userEmail: String, newTitle: String, newFirstName: String, newLastName: String) {
        // Reference to the user's data in the database
        val userReference = databaseReference.child(id)

        // Create a map with the new values
        val updates = mapOf(
            "title" to newTitle,
            "firstName" to newFirstName,
            "lastName" to newLastName
        )

        try {
            userReference.updateChildren(updates)
                .addOnSuccessListener {
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    // Pass the updated information back to MyInfoActivity
                    navigateToMyInfoActivity(userEmail, id)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
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
