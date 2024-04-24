package com.example.cbcs

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.cbcs.databinding.ActivitySelecourseBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SelecourseActivity : AppCompatActivity() {
    private var dept: String? = null
    private var program: String? = null
    private var course: String? = null
    private var course2: String? = null
    private var course3: String? = null
    private lateinit var binding: ActivitySelecourseBinding
    private lateinit var database: DatabaseReference
    private lateinit var progressBar: ProgressBar
    private lateinit var spinner2: Spinner
    private lateinit var spinner3: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelecourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Function to hide keyboard
        fun hideKeyboard() {
            val view: View? = this.currentFocus
            if (view != null) {
                val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        binding.imageBtn.setOnClickListener {
            hideKeyboard()
        }

        // Reference the spinners
        val deptSpinner = findViewById<Spinner>(R.id.txt_dept)
        val cbcsSpinner = findViewById<Spinner>(R.id.txt_cbcs)
        spinner2 = findViewById(R.id.txt_cbcs2)
        spinner3 = findViewById(R.id.txt_cbcs3)
        val prog_spinner = findViewById<Spinner>(R.id.txt_program)


        val spinner2Adapter = ArrayAdapter.createFromResource(
            this,
            R.array.CBCS_Common_1,
            android.R.layout.simple_spinner_item
        )
        spinner2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = spinner2Adapter

        val spinner3Adapter = ArrayAdapter.createFromResource(
            this,
            R.array.CBCS_Common_2,
            android.R.layout.simple_spinner_item
        )
        spinner3Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner3.adapter = spinner3Adapter

        // Create ArrayAdapter for the department spinner
        val deptAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.Departments,
            android.R.layout.simple_spinner_item
        )
        deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        deptSpinner.adapter = deptAdapter

        val program_Adapter = ArrayAdapter.createFromResource(this,R.array.Program,android.R.layout.simple_spinner_item)
        program_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        prog_spinner.adapter = program_Adapter

        prog_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Get the selected Program
                program = parent?.getItemAtPosition(position).toString()
                (view as? TextView)?.text = program // Set the selected value as the text of the spinner item
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing here
            }
        }



        // Create ArrayAdapter for the cbcs spinner (empty initially)
        val cbcsAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        cbcsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cbcsSpinner.adapter = cbcsAdapter

        progressBar = binding.progressBar1

        deptSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Clear the cbcs spinner's data
                cbcsAdapter.clear()

                // Get the selected department
                dept = parent?.getItemAtPosition(position).toString()

                when (dept) {
                    getString(R.string.Amity_Business_School) -> {
                        // Populate the cbcs spinner with courses for Amity Business School
                        val courses = resources.getStringArray(R.array.CBCS_Amity_Business_School)
                        cbcsAdapter.addAll(*courses)
                    }
                    getString(R.string.Amity_Institute_of_BioTechnology) -> {
                        // Populate the cbcs spinner with courses for Amity Institute of BioTechnology
                        val courses = resources.getStringArray(R.array.CBCS_Biotech)
                        cbcsAdapter.addAll(*courses)
                    }
                    getString(R.string.ASET_ECE) -> {
                        // Populate the cbcs spinner with courses for ASET(ECE)
                        val courses = resources.getStringArray(R.array.ASET_ECE)
                        cbcsAdapter.addAll(*courses)
                    }
                    getString(R.string.ASET_Mech) -> {
                        // Populate the cbcs spinner with courses for ASET(Mech)
                        val courses = resources.getStringArray(R.array.ASET_Mech)
                        cbcsAdapter.addAll(*courses)
                    }
                    getString(R.string.ASET_Chemistry) -> {
                        // Populate the cbcs spinner with courses for ASET chemistry
                        val courses = resources.getStringArray(R.array.ASET_Chemistry)
                        cbcsAdapter.addAll(*courses)
                    }
                    getString(R.string.Communication) -> {
                        // Populate the cbcs spinner with courses for Communication
                        val courses = resources.getStringArray(R.array.Amity_School_Communication)
                        cbcsAdapter.addAll(*courses)
                    }
                    getString(R.string.Fashion) -> {
                        // Populate the cbcs spinner with courses for Fashion
                        val courses = resources.getStringArray(R.array.Amity_fashion)
                        cbcsAdapter.addAll(*courses)
                    }
                    getString(R.string.Law) -> {
                        // Populate the cbcs spinner with courses for Law
                        val courses = resources.getStringArray(R.array.Amity_Law_School)
                        cbcsAdapter.addAll(*courses)
                    }
                    getString(R.string.Bhehavioural) -> {
                        // Populate the cbcs spinner with courses for Behavioural
                        val courses = resources.getStringArray(R.array.Aibas)
                        cbcsAdapter.addAll(*courses)
                    }
                    getString(R.string.Language) -> {
                        // Populate the cbcs spinner with courses for Languages
                        val courses = resources.getStringArray(R.array.Amity_languages)
                        cbcsAdapter.addAll(*courses)
                    }
                    getString(R.string.Social_Science) -> {
                        // Populate the cbcs spinner with courses for Social Science
                        val courses = resources.getStringArray(R.array.Amity_social_Sciences)
                        cbcsAdapter.addAll(*courses)
                    }
                    // Add more cases for other departments as needed
                }

                // Notify the adapter of the data change
                cbcsAdapter.notifyDataSetChanged()

                // Populate the choice spinners based on the selected department
                populateChoiceSpinners(dept,spinner2, spinner3)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing here
            }
        }

        cbcsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Get the selected selectedcourse1
                course = parent?.getItemAtPosition(position).toString()
                (view as? TextView)?.text = course // Set the selected value as the text of the spinner item
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing here
            }
        }

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Get the selected value from spinner2
                course2 = parent?.getItemAtPosition(position).toString()
                (view as? TextView)?.text = course2 // Set the selected value as the text of the spinner item
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case when nothing is selected
            }
        }

        spinner3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Get the selected value from spinner3
                course3 = parent?.getItemAtPosition(position).toString()
                (view as? TextView)?.text = course3 // Set the selected value as the text of the spinner item
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case when nothing is selected
            }
        }

        // Sending data to Firebase Realtime Database
        binding.submitBtn.setOnClickListener {
            val name = binding.txtName.text.toString()
            val enroll = binding.txtEnroll.text.toString()
            hideKeyboard()
            if (name.isBlank() || dept.isNullOrBlank() || course.isNullOrBlank() || enroll.isBlank()) {
                Toast.makeText(
                    this,
                    "Please fill all details properly",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                progressBar.visibility = View.VISIBLE
                database = FirebaseDatabase.getInstance().getReference("Students")
                val student = Student(name, enroll, dept, program, course, course2, course3)
                database.child(name).setValue(student)
                    .addOnSuccessListener {
                        progressBar.visibility = View.GONE
                        val intent = Intent(this, ThankYouActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            this,
                            "Failed, please try again after some time!!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
        }
    }

    private fun populateChoiceSpinners(department: String?, spinner2: Spinner, spinner3: Spinner) {
        val choice1List = ArrayList<String>()
        val choice2List = ArrayList<String>()


        choice1List.clear()
        choice2List.clear()

        when (department) {
            getString(R.string.Amity_Business_School) -> {
                val courses = resources.getStringArray(R.array.CBCS_Amity_Business_School)
                choice1List.addAll(courses)
                choice2List.addAll(courses)
            }
            getString(R.string.Amity_Institute_of_BioTechnology) -> {
                val courses = resources.getStringArray(R.array.CBCS_Biotech)
                choice1List.addAll(courses)
                choice2List.addAll(courses)
            }
            getString(R.string.ASET_ECE) -> {
                val courses = resources.getStringArray(R.array.ASET_ECE)
                choice1List.addAll(courses)
                choice2List.addAll(courses)
            }
            getString(R.string.ASET_Mech) -> {
                val courses = resources.getStringArray(R.array.ASET_Mech)
                choice1List.addAll(courses)
                choice2List.addAll(courses)
            }
            getString(R.string.ASET_Chemistry) -> {
                val courses = resources.getStringArray(R.array.ASET_Chemistry)
                choice1List.addAll(courses)
                choice2List.addAll(courses)
            }
            getString(R.string.Communication) -> {
                val courses = resources.getStringArray(R.array.Amity_School_Communication)
                choice1List.addAll(courses)
                choice2List.addAll(courses)
            }
            getString(R.string.Fashion) -> {
                val courses = resources.getStringArray(R.array.Amity_fashion)
                choice1List.addAll(courses)
                choice2List.addAll(courses)
            }
            getString(R.string.Law) -> {
                val courses = resources.getStringArray(R.array.Amity_Law_School)
                choice1List.addAll(courses)
                choice2List.addAll(courses)
            }
            getString(R.string.Language) -> {
                val courses = resources.getStringArray(R.array.Amity_languages)
                choice1List.addAll(courses)
                choice2List.addAll(courses)
            }
            getString(R.string.Social_Science) -> {
                val courses = resources.getStringArray(R.array.Amity_social_Sciences)
                choice1List.addAll(courses)
                choice2List.addAll(courses)
            }
            getString(R.string.Bhehavioural) ->{
                val courses = resources.getStringArray(R.array.Aibas)
                choice1List.addAll(courses)
                choice2List.addAll(courses)
            }
            // Add more cases for other departments as needed
        }

        val choice1Adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, choice1List)
        val choice2Adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, choice2List)

        choice1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        choice2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner2.adapter = choice1Adapter
        spinner3.adapter = choice2Adapter
    }
}
