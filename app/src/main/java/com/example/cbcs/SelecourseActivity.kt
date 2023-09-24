package com.example.cbcs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cbcs.databinding.ActivitySelecourseBinding
import com.google.firebase.auth.FirebaseAuth

class SelecourseActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelecourseBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelecourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitBtn.setOnClickListener {
            val intent = Intent(this,ThankYouActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}