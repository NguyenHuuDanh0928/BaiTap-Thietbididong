package com.example.demothongtin
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtName = findViewById<EditText>(R.id.edtName)
        val edtAge = findViewById<EditText>(R.id.edtAge)
        val btnCheck = findViewById<Button>(R.id.btnCheck)

        btnCheck.setOnClickListener {
            val name = edtName.text.toString().trim()
            val ageStr = edtAge.text.toString().trim()

            if (name.isEmpty() || ageStr.isEmpty()) {
                showDialog("Lỗi", "Vui lòng nhập đầy đủ thông tin!")
                return@setOnClickListener
            }

            val age = ageStr.toIntOrNull()
            if (age == null || age < 0) {
                showDialog("Lỗi", "Tuổi phải là số hợp lệ!")
                return@setOnClickListener
            }

            val category = when {
                age > 65 -> "Người già"
                age in 6..65 -> "Người lớn"
                age in 2..5 -> "Trẻ em"
                else -> "Em bé"
            }

            showDialog("Kết quả", "$name là $category")
        }
    }

    private fun showDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
