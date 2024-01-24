package com.example.cbcs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.text.HtmlCompat

class infoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val infoTextView: TextView = findViewById(R.id.info_text)
        val htmlFormattedString = getString(R.string.info_text_view_content)
        infoTextView.text = HtmlCompat.fromHtml(htmlFormattedString, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}