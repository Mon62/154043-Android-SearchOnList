package com.example.searchonlist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var searchInput : EditText
    private lateinit var studentList: ListView
    private lateinit var students: List<StudentModel>
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        searchInput = findViewById(R.id.searchInput)
        studentList = findViewById(R.id.studentList)
        students = generateStudentList()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, students.map { it.name })
        studentList.adapter = adapter

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                filterStudentList(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                filterStudentList(s.toString())
            }
        })

        filterStudentList("")
    }

    private  fun generateStudentList(): List<StudentModel> {
        val students = mutableListOf<StudentModel>()
        for (i in 10000..10062) {
            val student = StudentModel(name = "Student $i", id = "ID $i")
            students.add(student)
        }
        return students
    }

    private fun filterStudentList(query: String) {
        val filteredStudents = if (query.length <= 2) {
            students
        } else {
            students.filter { it.name.contains(query, ignoreCase = true) || it.id.contains(query, ignoreCase = true) }
        }
        adapter.clear()
        adapter.addAll(filteredStudents.map { "${it.name} - MSSV ${it.id}" })
        adapter.notifyDataSetChanged()
    }
}