package com.example.cbcs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.cbcs.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding   //initializing variable binding for view binding
    private lateinit var firebaseAuth: FirebaseAuth     //initializing variable firebaseauth of type FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()        //getting firebase instance
        binding.loginbtn.setOnClickListener {
            val email=binding.editTxtEmailAddress.text.toString()
            val password=binding.editTxtPassword.text.toString()

            if(email.isBlank() || password.isBlank()){                      //Checking all feilds are non-empty
                Toast.makeText(this,"Please fill Details Properly",Toast.LENGTH_SHORT).show()
            }

            if (email.isNotEmpty()&&password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {//Authenticating
                    if (it.isSuccessful){
                        val intent = Intent(this,SelecourseActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this,"Invalid Email or Password",Toast.LENGTH_LONG).show()
                    }
                }
            }else{
                Toast.makeText(this,"Please fill Details Properly",Toast.LENGTH_LONG).show()
            }
        }

    }
}