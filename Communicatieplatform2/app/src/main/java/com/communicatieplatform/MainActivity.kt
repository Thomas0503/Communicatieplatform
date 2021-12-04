package com.communicatieplatform

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.communicatieplatform.databinding.ActivityLoginBinding
import com.communicatieplatform.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainActivity:AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.LoginButton.setOnClickListener {
            performLogin()
        }


    }

    private fun performLogin() {
        val email = binding.editTextTextEmailAddress.text.toString()
        val password = binding.editTextTextPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "E-mailadres/wachtwoord is niet ingevuld.", Toast.LENGTH_LONG).show()
            return
        }

        if (password.length < 6) {
            Toast.makeText(this, "Dit wachtwoord is te kort, vul een correct wachtwoord in!", Toast.LENGTH_LONG)
                .show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                Log.d("Login", "Successfully logged in: ${it.result?.user?.uid}")
                //Login
                val intent = Intent(this, Homepage::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Mislukt om in te loggen: Het wachtwoord is verkeerd of de gebruiker heeft geen account.", Toast.LENGTH_LONG).show()
            }


    }


}