package com.example.cbcs

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import com.example.cbcs.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding   //initializing variable binding for view binding
    private lateinit var firebaseAuth: FirebaseAuth     //initializing variable firebaseauth of type FirebaseAuth
    private  lateinit var progressBar: ProgressBar      // initilizing varilable progressbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //function to hide keyboard
        fun hidekeyboard () {
            val view: View?=this.currentFocus
            if (view!=null){
                val imm: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE)as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken,0)
            }

        }
        binding.imgbtn.setOnClickListener {             //hiding keyboard
            hidekeyboard()
        }
        binding.fabMain.setOnClickListener {        // navigating ti info Activity
            val intent = Intent(this,infoActivity::class.java)
            startActivity(intent)
        }
        progressBar = binding.progressBar
        firebaseAuth= FirebaseAuth.getInstance()        //getting firebase instance
        binding.loginbtn.setOnClickListener {
            val email = binding.editTxtEmailAddress.text.toString()
            val password = binding.editTxtPassword.text.toString()

            if (email.isBlank() || password.isBlank()) {                      //Checking all feilds are non-empty
                Toast.makeText(this, "Please fill Details Properly", Toast.LENGTH_SHORT).show()
            } else {
                progressBar.visibility = View.VISIBLE

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { //Authenticating
                            if (it.isSuccessful) {
                                progressBar.visibility = View.GONE
                                val intent = Intent(this, SelecourseActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Please fill Details Properly", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
