package com.tsu.firstlab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.tsu.firstlab.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.signUpButton.setOnClickListener{

            // checks if all strings was entered

            if (binding.emailInputEditText.text.toString().isNotEmpty() &&
                    binding.nameInputEditText.text.toString().isNotEmpty() &&
                    binding.passwordInputEditText.text.toString().isNotEmpty()) {
                val intent = Intent(this, MainMenuActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Sign Up error!")
                dialogBuilder.setMessage("Some of your fields missing any text.")
                dialogBuilder.show()
            }
        }

        binding.backButton.setOnClickListener{
            super.onBackPressed()
        }
    }


}