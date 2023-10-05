package com.example.cbcs

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.cbcs.databinding.ActivitySelecourseBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SelecourseActivity : AppCompatActivity() {
    private var dept: String? =null                 //initilaizing variables
    private var course: String? =null
    private lateinit var binding: ActivitySelecourseBinding     //Initilazing View Binding
    private lateinit var database: DatabaseReference            //Initilazing database Reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelecourseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //function to hide keyboard
        fun hidekeyboard () {
            val view: View?=this.currentFocus
            if (view!=null){
                val imm: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE)as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken,0)
            }

        }

        binding.imageBtn.setOnClickListener {               //hiding keyboard
            hidekeyboard()
        }
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

                when (dept) {
                    getString(R.string.Amity_Business_School) -> {
                        // Populate the cbcs spinner with courses for Amity Business School
                        val courses = resources.getStringArray(R.array.CBCS_Common)
                        cbcsAdapter.addAll(*courses)

                    }
                    getString(R.string.Amity_Institute_of_BioTechnology) -> {
                        // Populate the cbcs spinner with courses for Amity Institute of BioTechnology
                        val courses = resources.getStringArray(R.array.CBCS_Common)
                        cbcsAdapter.addAll(*courses)

                    }
                    getString(R.string.ASET_CSE) -> {
                        // Populate the cbcs spinner with courses for ASET(CSE)
                        val courses = resources.getStringArray(R.array.ASET_CSE)
                        cbcsAdapter.addAll(*courses)

                    }
                    getString(R.string.ASET_ECE) -> {
                        // Populate the cbcs spinner with courses for ASET(ECE)
                        val courses = resources.getStringArray(R.array.CBCS_Common)
                        cbcsAdapter.addAll(*courses)

                    }
                    getString(R.string.ASET_Mech) -> {
                        // Populate the cbcs spinner with courses for ASET(Mech)
                        val courses = resources.getStringArray(R.array.CBCS_Common)
                        cbcsAdapter.addAll(*courses)
                        //course=courses.toString()
                    }
                    getString(R.string.ASET_Chemistry) -> {
                        // Populate the cbcs spinner with courses for ASET chemistry
                        val courses = resources.getStringArray(R.array.CBCS_Common)
                        cbcsAdapter.addAll(*courses)

                    }
                    getString(R.string.Communication) -> {
                        // Populate the cbcs spinner with courses for Communication
                        val courses = resources.getStringArray(R.array.CBCS_Common)
                        cbcsAdapter.addAll(*courses)

                    }
                    getString(R.string.Fashion) -> {
                        // Populate the cbcs spinner with courses for Fashion
                        val courses = resources.getStringArray(R.array.CBCS_Common)
                        cbcsAdapter.addAll(*courses)

                    }
                    getString(R.string.Law) -> {
                        // Populate the cbcs spinner with courses for Law
                        val courses = resources.getStringArray(R.array.CBCS_Common)
                        cbcsAdapter.addAll(*courses)

                    }
                    getString(R.string.Bhehavioural) -> {
                        // Populate the cbcs spinner with courses for Behavioural
                        val courses = resources.getStringArray(R.array.CBCS_Common)
                        cbcsAdapter.addAll(*courses)
                    }
                    getString(R.string.Language) -> {
                        // Populate the cbcs spinner with courses for LAnguages
                        val courses = resources.getStringArray(R.array.CBCS_Common)
                        cbcsAdapter.addAll(*courses)

                    }
                    getString(R.string.Social_Science) -> {
                        // Populate the cbcs spinner with courses for Social Science
                        val courses = resources.getStringArray(R.array.CBCS_Common)
                        cbcsAdapter.addAll(*courses)

                    }
                }
                // Add more conditions for other departments as needed

                // Notify the adapter of the data change
                cbcsAdapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing here
            }


        }
        cbcsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                // Get the selected course
                course = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing here
            }
        }

        //Sending data to Firebase Realtime Database
        binding.submitBtn.setOnClickListener {
            val name=binding.txtName.text.toString()  //Getting Name of Student
            val enroll =binding.txtEnroll.text.toString()   //Getting Enrollment Number of Student
            if(name.isBlank() || dept!!.isBlank() || course!!.isBlank() || enroll.isBlank()){       //CHECKING ALL FEILDS are filled or nt
                Toast.makeText(this,"Please All fill Details Properly",Toast.LENGTH_SHORT).show()
            }else {
                database = FirebaseDatabase.getInstance().getReference("Students")      //Getting Firebase Instance
                val student = Student(name,enroll,dept,course)                               // creating Object of Data class Students
                database.child(name).setValue(student).addOnSuccessListener {         //Setting VAlues
                    val intent = Intent(this, ThankYouActivity::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {                                           // adding a Failure Listner
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