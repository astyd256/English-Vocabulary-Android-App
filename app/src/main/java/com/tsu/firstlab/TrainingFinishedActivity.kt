package com.tsu.firstlab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tsu.firstlab.databinding.ActivityTrainingBinding
import com.tsu.firstlab.databinding.ActivityTrainingFinishedBinding
import com.tsu.firstlab.databinding.FragmentDictionaryBinding

class TrainingFinishedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrainingFinishedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrainingFinishedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        binding.textCorrect.text = "Correct: ${intent!!.getStringExtra("correct").toString()}"
        binding.textIncorrect.text = "Incorrect: ${intent!!.getStringExtra("incorrect").toString()}"

        binding.againButton.setOnClickListener {
            val intent = Intent(this, TrainingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}