package com.example.cbcs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cbcs.databinding.ActivityMainBinding
import com.example.cbcs.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class signupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding   //initializing variable binding for view binding
    private lateinit var firebaseAuth: FirebaseAuth     //initializing variable firebaseauth of type FirebaseAuth
    private  lateinit var progressBar: ProgressBar      // initilizing varilable progressbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth= FirebaseAuth.getInstance()
        progressBar = binding.progressBar3
        binding.createaccbtn.setOnClickListener {
            val email = binding.signupEmailAddress.text.toString()
            val password = binding.signupPassword.text.toString()

            if (email.isBlank() || password.isBlank()) {                      //Checking all feilds are non-empty
                Toast.makeText(this, "Please fill Details Properly", Toast.LENGTH_SHORT).show()
            }else if (!email.endsWith("@s.amity.edu")) {
                Toast.makeText(
                    this,
                    "Please Use your School I'd  to Login",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else{
                progressBar.visibility = View.VISIBLE
                if (email.isNotEmpty() && password.isNotEmpty()){
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(this, "Account Created successfully!! Kindly Login", Toast.LENGTH_SHORT).show()
                            progressBar.visibility = View.GONE
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                        else {
                            Toast.makeText(this, "Please Try After Some Time!!", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
                else {
                    Toast.makeText(this, "Please fill Details Properly", Toast.LENGTH_LONG).show()
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}