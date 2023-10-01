package com.example.cbcs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.cbcs.databinding.ActivitySelecourseBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SelecourseActivity : AppCompatActivity() {
    private var dept: String? =null
    private var course: String? =null
    private lateinit var binding: ActivitySelecourseBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelecourseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Reference the spinners
        val deptSpinner = findViewById<Spinner>(R.id.txt_dept)
        val cbcsSpinner = findViewById<Spinner>(R.id.txt_cbcs)

        // Create ArrayAdapter for the department spinner
        val deptAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.Departments,
            android.R.layout.simple_spinner_item
        )
        deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        deptSpinner.adapter = deptAdapter

        // Create ArrayAdapter for the cbcs spinner (empty initially)
        val cbcsAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        cbcsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cbcsSpinner.adapter = cbcsAdapter // Updated reference here

        deptSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                // Clear the cbcs spinner's data
                cbcsAdapter.clear()

                // Get the selected department
                dept = parent?.getItemAtPosition(position).toString()

                if (dept == getString(R.string.Amity_Business_School)) {
                    // Populate the cbcs spinner with courses for Amity Business School
                    val courses = resources.getStringArray(R.array.CBCS_Amity_Business_School)
                    cbcsAdapter.addAll(*courses)
                    course=courses.toString()
                } else if (dept == getString(R.string.Amity_Institute_of_BioTechnology)) {
                    // Populate the cbcs spinner with courses for Amity Institute of BioTechnology
                    val courses = resources.getStringArray(R.array.CBCS_Biotech)
                    cbcsAdapter.addAll(*courses)
                    course=courses.toString()
                } else if (dept == getString(R.string.ASET_CSE)) {
                    // Populate the cbcs spinner with courses for ASET(CSE)
                    val courses = resources.getStringArray(R.array.ASET_CSE)
                    cbcsAdapter.addAll(*courses)
                    course=courses.toString()
                }
                // Add more conditions for other departments as needed

                // Notify the adapter of the data change
                cbcsAdapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing here
            }

        }




        binding.submitBtn.setOnClickListener {
            val name=binding.txtName.text.toString()
            val enroll =binding.txtEnroll.text.toString()
            if(name.isBlank() || dept!!.isBlank() || course!!.isBlank() || enroll.isBlank()){
                Toast.makeText(this,"Please All fill Details Properly",Toast.LENGTH_SHORT).show()
            }else {
                database = FirebaseDatabase.getInstance().getReference("Students")
                val Student = Student(name,enroll,dept,course)
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