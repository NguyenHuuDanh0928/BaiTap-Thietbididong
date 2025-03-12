package com.example.demoquanlithuvien

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Danh sách người dùng (người mượn sách)
    private val userList = mutableListOf("Nguyen Van A", "Tran Thi B", "Le Van C", "Pham Thi D")

    // Map lưu trữ sách đã mượn của từng người dùng
    private val borrowedBooks = mutableMapOf<String, MutableList<String>>()

    // Danh sách sách có sẵn
    private val availableBooks = mutableListOf("Sách 01", "Sách 02")

    // Biến để theo dõi người dùng hiện tại
    private var currentUser = "Nguyen Van A"

    // Các view
    private lateinit var etStaffName: EditText
    private lateinit var btnChange: Button
    private lateinit var layoutBookList: LinearLayout
    private lateinit var btnAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Khởi tạo UI và listeners
        initializeViews()
        setupListeners()
        updateBookList()
    }

    private fun initializeViews() {
        etStaffName = findViewById(R.id.etStaffName)
        btnChange = findViewById(R.id.btnChange)
        layoutBookList = findViewById(R.id.layoutBookList)
        btnAdd = findViewById(R.id.btnAdd)

        // Hiển thị người dùng hiện tại
        etStaffName.setText(currentUser)
    }

    private fun setupListeners() {
        // Xử lý nút "Đổi" người dùng
        btnChange.setOnClickListener {
            showUserSelectionDialog()
        }

        // Xử lý nút "Thêm" sách mới
        btnAdd.setOnClickListener {
            addNewBook()
        }
    }

    // Hiển thị dialog để chọn người dùng
    private fun showUserSelectionDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Chọn người mượn sách")

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userList)

        builder.setAdapter(adapter) { _, which ->
            // Cập nhật người dùng hiện tại
            currentUser = userList[which]
            etStaffName.setText(currentUser)

            // Cập nhật danh sách sách dựa trên người dùng mới
            updateBookList()
        }

        // Thêm nút để xem sách đã mượn
        builder.setPositiveButton("Xem sách đã mượn") { _, _ ->
            showBorrowedBooks()
        }

        builder.setNegativeButton("Hủy", null)

        builder.show()
    }

    // Hiển thị danh sách sách đã mượn của người dùng hiện tại
    private fun showBorrowedBooks() {
        val books = borrowedBooks[currentUser] ?: mutableListOf()

        if (books.isEmpty()) {
            Toast.makeText(this, "$currentUser chưa mượn sách nào", Toast.LENGTH_SHORT).show()
            return
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sách đã mượn của $currentUser")

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, books)
        builder.setAdapter(adapter, null)

        builder.setPositiveButton("Đóng", null)
        builder.show()
    }

    // Thêm sách mới vào danh sách
    private fun addNewBook() {
        val nextBookNumber = availableBooks.size + 1
        val newBookName = "Sách ${String.format("%02d", nextBookNumber)}"
        availableBooks.add(newBookName)

        // Cập nhật UI
        updateBookList()

        Toast.makeText(this, "Đã thêm $newBookName vào danh sách", Toast.LENGTH_SHORT).show()
    }

    // Cập nhật danh sách sách trong UI
    private fun updateBookList() {
        // Xóa danh sách hiện tại
        layoutBookList.removeAllViews()

        // Thêm lại các mục sách
        for (book in availableBooks) {
            val bookItemView = LayoutInflater.from(this)
                .inflate(R.layout.book_item, layoutBookList, false)

            val checkbox = bookItemView.findViewById<CheckBox>(R.id.cbBook)
            val textView = bookItemView.findViewById<TextView>(R.id.tvBookTitle)

            textView.text = book

            // Kiểm tra xem sách này có được mượn bởi người dùng hiện tại không
            val userBorrowedBooks = borrowedBooks[currentUser] ?: mutableListOf()
            checkbox.isChecked = userBorrowedBooks.contains(book)

            // Xử lý sự kiện khi checkbox thay đổi
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // Thêm sách vào danh sách đã mượn
                    if (!userBorrowedBooks.contains(book)) {
                        userBorrowedBooks.add(book)
                        borrowedBooks[currentUser] = userBorrowedBooks
                        Toast.makeText(this, "Đã mượn: $book", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Xóa sách khỏi danh sách đã mượn
                    userBorrowedBooks.remove(book)
                    borrowedBooks[currentUser] = userBorrowedBooks
                    Toast.makeText(this, "Đã trả: $book", Toast.LENGTH_SHORT).show()
                }
            }

            layoutBookList.addView(bookItemView)
        }
    }
}