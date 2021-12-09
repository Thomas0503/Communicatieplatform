package com.communicatieplatform

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.communicatieplatform.databinding.ActivityLoginBinding
import com.communicatieplatform.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


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

    //class FirebaseUtils {
    //    val fireStoreDatabase = FirebaseFirestore.getInstance()
    //}
    //val db = FirebaseFirestore.getInstance()
    //private fun readData(){
    //    binding.LoginButton.setOnClickListener {
    //        FirebaseUtils().fireStoreDatabase.collection("users")
    //                .get()
    //                .addOnSuccessListener { querySnapshot ->
    //                    querySnapshot.forEach { document ->
    //                        Log.d(TAG, "Read document with ID ${document.id}")
    //                    }
    //                }
    //                .addOnFailureListener { exception ->
    //                    Log.w(TAG, "Error getting documents $exception")
    //                }
    //    }
    //}

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
                val user = Firebase.auth.currentUser
                val uid = null
                user?.let {val uid = user.uid
                }
                val db = Firebase.firestore
                db.collection("users").document(user?.uid.toString()).get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            val trainer = document.get("trainer")
                            if(trainer == true){
                                val pleeggezin:ArrayList<String> = document.get("pleeggezinnen") as ArrayList<String>
                                val intent = Intent(this, TrainerHomepage::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                                intent.putExtra("pleeggezin", pleeggezin)
                                startActivity(intent)
                            } else if(trainer != null) {
                                val intent = Intent(this, Homepage::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "1Onverwachte fout, probeer opnieuw", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(this, "2Onverwachte fout, probeer opnieuw", Toast.LENGTH_LONG).show()

                            Log.d(TAG, "No such document")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, "3     Onverwachte fout, probeer opnieuw", Toast.LENGTH_LONG).show()
                        Log.d(TAG, "get failed with ", exception)
                    }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Mislukt om in te loggen: Het wachtwoord is verkeerd of de gebruiker heeft geen account.", Toast.LENGTH_LONG).show()
            }


    }



}