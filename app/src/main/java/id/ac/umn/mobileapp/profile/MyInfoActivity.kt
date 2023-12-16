package id.ac.umn.mobileapp.profile

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import id.ac.umn.mobileapp.R
import id.ac.umn.mobileapp.profile.address.EditAddressActivity
import id.ac.umn.mobileapp.profile.logininformation.EditPasswordActivity
import id.ac.umn.mobileapp.profile.myprofile.EditMyPorfileActivity

class MyInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_info)

        // Temukan tombol-tombol berdasarkan ID
        val ibMyInfo: ImageButton = findViewById(R.id.ibMyProfile)
        val ibAddress: ImageButton = findViewById(R.id.ibEditAddress)
        val ibLoginInformation: ImageButton = findViewById(R.id.ibLoginInformation)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Tambahkan ikon navigasi
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Tambahkan onClickListener untuk ikon navigasi
        toolbar.setNavigationOnClickListener {
            // Kembali ke fragment sebelumnya atau lakukan operasi yang sesuai
            onBackPressed()
        }

        // Tambahkan onClickListener untuk setiap tombol
        ibMyInfo.setOnClickListener {
            // Intent untuk pindah ke activity lain (gantilah MyOtherActivity dengan nama activity yang sesuai)
            val intent = Intent(this, EditMyPorfileActivity::class.java)
            startActivity(intent)
        }

        ibAddress.setOnClickListener {
            // Intent untuk pindah ke activity lain (gantilah MyAddressActivity dengan nama activity yang sesuai)
            val intent = Intent(this, EditAddressActivity::class.java)
            startActivity(intent)
        }

        ibLoginInformation.setOnClickListener {
            // Intent untuk pindah ke activity lain (gantilah MyLoginInfoActivity dengan nama activity yang sesuai)
            val intent = Intent(this, EditPasswordActivity::class.java)
            startActivity(intent)
        }
    }

}
