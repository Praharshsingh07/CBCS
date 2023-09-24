package com.example.cbcs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cbcs.databinding.ActivitySelecourseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SelecourseActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelecourseBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelecourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitBtn.setOnClickListener {
            val name=binding.txtName.text.toString()
            val department=binding.txtDept.text.toString()
            val course =binding.txtCourse.text.toString()
            val enroll =binding.txtEnroll.text.toString()
            val cbcs=binding.txtCbcs.text.toString()
            if(name.isBlank() || department.isBlank() || course.isBlank() || enroll.isBlank() || cbcs.isBlank()){
                Toast.makeText(this,"Please All fill Details Properly",Toast.LENGTH_SHORT).show()
            }else {
                database = FirebaseDatabase.getInstance().getReference("Students")
                val Student = Student(name, department, course, enroll, cbcs)
                database.child(name).setValue(Student).addOnSuccessListener {
                    val intent = Intent(this, ThankYouActivity::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(
                        this,
                        "Failed please try again after some time!!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
    }
}