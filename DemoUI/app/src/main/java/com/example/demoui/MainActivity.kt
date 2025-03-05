package com.example.demoui

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.demoui.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {

            Toast.makeText(this, "Quay lại màn hình trước", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }


        val editButton = findViewById<ImageButton>(R.id.editButton)
        editButton.setOnClickListener {
            Toast.makeText(this, "Chỉnh sửa thông tin", Toast.LENGTH_SHORT).show()
        }


        val profileImage = findViewById<ImageView>(R.id.profileImage)

    }
}