package id.ac.umn.mobileapp.profile

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.ac.umn.mobileapp.R
import id.ac.umn.mobileapp.profile.address.EditAddressActivity
import id.ac.umn.mobileapp.profile.logininformation.EditPasswordActivity
import id.ac.umn.mobileapp.profile.myprofile.EditMyProfileActivity

class MyInfoActivity : AppCompatActivity() {
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

    companion object {
        const val EDIT_PROFILE_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        databaseReference = FirebaseDatabase.getInstance().getReference("users")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_info)

        // Initialize the database reference

        // Get the email from the intent
        val email = intent.getStringExtra("email")
        val id = intent.getStringExtra("id")
        if (email != null && id!= null) {
            // Set up the UI components
            val ibMyInfo: ImageButton = findViewById(R.id.ibMyProfile)
            val ibAddress: ImageButton = findViewById(R.id.ibEditAddress)
            val ibLoginInformation : ImageButton = findViewById(R.id.ibLoginInformation)
            val tvTitle: TextView = findViewById(R.id.tvTitle)
            val tvFirstName: TextView = findViewById(R.id.tvFirstName)
            val tvLastName: TextView = findViewById(R.id.tvLastName)
            val tvPhone: TextView = findViewById(R.id.tvTelepon)
            val tvAddress: TextView = findViewById(R.id.tvAddress)
            val tvEmail: TextView = findViewById(R.id.tvEmail)
            val tvPassword: TextView = findViewById(R.id.tvPassword)

            // Set up the toolbar
            val toolbar: Toolbar = findViewById(R.id.toolbar)
            setSupportActionBar(toolbar)

            // Tambahkan ikon navigasi
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            // Tambahkan onClickListener untuk ikon navigasi
            toolbar.setNavigationOnClickListener {
                // Kembali ke fragment sebelumnya atau lakukan operasi yang sesuai
                onBackPressed()
            }

            // Query to find the user with the matching email
            val query = databaseReference.orderByChild("email").equalTo(email)

            // Listen for changes in the database
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called when data changes in the database

                    // Check if the query returned any results
                    if (dataSnapshot.exists()) {
                        for (userSnapshot in dataSnapshot.children) {
                            // Retrieve data from Firebase
                            val title = userSnapshot.child("title").getValue(String::class.java)
                            val lastName = userSnapshot.child("lastName").getValue(String::class.java)
                            val firstName = userSnapshot.child("firstName").getValue(String::class.java)
                            val phoneNumber = userSnapshot.child("phoneNumber").getValue(String::class.java)
                            val address = userSnapshot.child("address").getValue(String::class.java)
                            val email = userSnapshot.child("email").getValue(String::class.java)
                            val password = userSnapshot.child("password").getValue(String::class.java)

                            // Update the TextViews with the retrieved data
                            tvTitle.text = title
                            tvFirstName.text = firstName
                            tvLastName.text = lastName
                            tvPhone.text = phoneNumber
                            tvAddress.text = address
                            tvEmail.text = email
                            tvPassword.text = password
                        }
                    } else {
                        // Handle the case where no user with the specified email was found
                        // You can show an error message or take appropriate action
                        Log.e("error", "error database")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("error", "error database")
                }
            })

            // Set up the onClickListener for Edit My Profile button
            ibMyInfo.setOnClickListener {
                // Intent untuk pindah ke activity lain (gantilah MyOtherActivity dengan nama activity yang sesuai)
                val intent = Intent(this, EditMyProfileActivity::class.java)
                intent.putExtra("email", email)
                intent.putExtra("id",id)
                startActivity(intent)
                finish()
            }

            ibAddress.setOnClickListener{
                val intent = Intent(this, EditAddressActivity::class.java)
                intent.putExtra("email",email)
                intent.putExtra("id",id)
                startActivity(intent)
                finish()
            }
            ibLoginInformation.setOnClickListener{
                val intent = Intent(this,EditPasswordActivity::class.java)
                intent.putExtra("email", email)
                intent.putExtra("id",id)
                startActivity(intent)
                finish()
            }
        }
    }

    // Override onActivityResult to handle the result

}
