package id.ac.umn.mobileapp.profile.myprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import id.ac.umn.mobileapp.R

class EditMyPorfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_my_porfile)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Tambahkan ikon navigasi
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Tambahkan onClickListener untuk ikon navigasi
        toolbar.setNavigationOnClickListener {
            // Kembali ke fragment sebelumnya atau lakukan operasi yang sesuai
            finish()
        }
    }
}