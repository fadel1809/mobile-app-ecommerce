package id.ac.umn.mobileapp.profile

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView

import id.ac.umn.mobileapp.R
import id.ac.umn.mobileapp.order.OrderActivity
import id.ac.umn.mobileapp.wishlist.WishlistActivity

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
    private lateinit var profileImage: CircleImageView // Move the variable to the class level

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
        val btnMyOrders: Button = view.findViewById(R.id.btnMyOrders)
        val btnWishlist: Button = view.findViewById(R.id.btnWishlist)
        val tvName: TextView = view.findViewById(R.id.tvNama)
        profileImage= view.findViewById(R.id.profile_image)

        // Load the saved image URI from SharedPreferences
        val savedImageUri = getSavedImageUri()
        if (savedImageUri != null) {
            // If a saved image URI exists, load it into the CircleImageView using Glide
            Glide.with(requireContext())
                .load(savedImageUri)
                .into(profileImage)
        }

        profileImage.setOnClickListener{
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
        }

        btnWishlist.setOnClickListener{
            val intent = Intent(activity,WishlistActivity::class.java)
            intent.putExtra("email",email)
            intent.putExtra("id",id)
            startActivity(intent)
        }
        btnMyOrders.setOnClickListener {
//          Pindah ke halaman MyInfoActivity
            val intent = Intent(activity, OrderActivity::class.java)
            intent.putExtra("email",email)
            intent.putExtra("id",id)
            startActivity(intent)
        }

        btnMyInfo.setOnClickListener {
//          Pindah ke halaman MyInfoActivity
            val intent = Intent(activity, MyInfoActivity::class.java)
            intent.putExtra("email",email)
            intent.putExtra("id",id)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            val sharedPreferences= context?.getSharedPreferences("user_data", Context.MODE_PRIVATE)
            val email =  sharedPreferences?.getString("email", null)
            val id =  sharedPreferences?.getString("id",null)

            if(email != null && id != null){
                val editor = sharedPreferences?.edit()
                editor?.remove("email")
                editor?.remove("id")
                editor?.apply()
                val profileFragment = ProfileFragment() // Ganti dengan nama kelas fragment profil Anda
                val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction?.replace(R.id.frame_container, profileFragment) // Ganti dengan ID container fragment Anda
                transaction?.addToBackStack(null)
                transaction?.commit()
            }else{
                val editor = sharedPreferences?.edit()
                editor?.putString("email",email)
                editor?.putString("id",id)
                editor?.apply()
                val loginFragment = LoginFragment()// Ganti dengan nama kelas fragment profil Anda
                val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction?.replace(R.id.frame_container, loginFragment) // Ganti dengan ID container fragment Anda
                transaction?.addToBackStack(null)
                transaction?.commit()
            }


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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val selectedImageUri: Uri = data.data ?: return

            // Load the selected image into the CircleImageView using Glide
            Glide.with(requireContext())
                .load(selectedImageUri)
                .into(profileImage)

            // Save the image URI to SharedPreferences
            saveImageUriToPreferences(selectedImageUri)
        }
    }
    private fun saveImageUriToPreferences(imageUri: Uri) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString("profile_image_uri", imageUri.toString())
        editor.apply()
    }

    // Fungsi untuk mengambil URI gambar dari Shared Preferences
    private fun getSavedImageUri(): Uri? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val uriString = preferences.getString("profile_image_uri", null)
        return if (uriString != null) Uri.parse(uriString) else null
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 123 // Anda dapat mengganti angka ini sesuai keinginan Anda
    }
}